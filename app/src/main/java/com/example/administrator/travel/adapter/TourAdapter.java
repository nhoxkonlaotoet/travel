package com.example.administrator.travel.adapter;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.models.bases.ExternalStorageInteractor;
import com.example.administrator.travel.models.bases.TourInteractor;
import com.example.administrator.travel.models.entities.Tour;
import com.example.administrator.travel.models.impls.ExternalStorageInteractorImpl;
import com.example.administrator.travel.models.impls.TourInteractorImpl;
import com.example.administrator.travel.models.listeners.Listener;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by Admin on 6/1/2019.
 */

public class TourAdapter extends RecyclerView.Adapter<TourAdapter.ViewHolder>
        implements Listener.OnGetTourImageFinishedListener, Listener.OnLoadImageThumpnailFinishedListener {
    private LayoutInflater mInflater;
    private TourAdapter.ItemClickListener mClickListener;
    private RecyclerView parent;

    private List<Tour> tourList;
    private HashMap<String, Double> ratingMap;
    private HashMap<String, Bitmap> tourImageMap;

    private TourInteractor tourInteractor;
    private ExternalStorageInteractor externalStorageInteractor;

    private String toursPath;
    private boolean[] loadPhotoFlags;
    private boolean externalStoragePermissionGranted;
    int photoWidth, photoHeight;

    public TourAdapter(Context context, List<Tour> tourList, HashMap<String, Double> ratingMap) {
        if (context == null)
            return;
        mInflater = LayoutInflater.from(context);
        this.tourList = tourList;
        this.ratingMap = ratingMap;
        tourImageMap = new HashMap<>();
        tourInteractor = new TourInteractorImpl();
        loadPhotoFlags = new boolean[tourList.size()];
        initUsingExternalStorage(context);
        photoWidth = (int)context.getResources().getDimension(R.dimen.tour_image_thumpnail_width);
        photoHeight = (int)context.getResources().getDimension(R.dimen.tour_image_thumpnail_height);
    }


    private void initUsingExternalStorage(Context context) {
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            externalStoragePermissionGranted = true;
            externalStorageInteractor = new ExternalStorageInteractorImpl();
            toursPath = context.getString(R.string.external_storage_path_tours);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        parent = recyclerView;
    }

    @Override
    @NonNull
    public TourAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mInflater == null)
            return null;
        View view;
        view = mInflater.inflate(R.layout.item_tour, parent, false);

        return new TourAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TourAdapter.ViewHolder holder, int position) {
        Tour tour = tourList.get(position);

        if (!loadPhotoFlags[position]) {
            Random rand = new Random();
            int getRandomTourPhoto = rand.nextInt(tour.numberofImages);

            String currentTourPath = toursPath + tour.id + "/";
            if (externalStoragePermissionGranted
                    && externalStorageInteractor.isExistFile(currentTourPath, tour.id + getRandomTourPhoto)) {
                externalStorageInteractor.getBitmapThumpnailFromExternalFile(currentTourPath, tour.id + getRandomTourPhoto, this);
                Log.e("fromSDcard0: ", currentTourPath + "    " + tour.id + " " + getRandomTourPhoto);
            } else {
                tourInteractor.getTourImage(getRandomTourPhoto, tour.id, this);
                Log.e("fromFirebase: ", tour.id + " " + getRandomTourPhoto);
            }
            loadPhotoFlags[position] = true;
        }
        if (tourImageMap.get(tour.id) != null)
            holder.imgvTourPhoto.setImageBitmap(tourImageMap.get(tour.id));
        // holder.imgvTourPhoto.setImageResource(R.drawable.tour);
        holder.txtTourName.setText(tour.name);
        holder.txtTourPrice.setText(String.valueOf(tour.adultPrice) + "đ");
        if (tour.ratings != null) {
            holder.txtNumberofRating.setText(String.valueOf(tour.ratings.size() + " lượt đánh giá"));
            holder.txtRating.setText(String.valueOf(ratingMap.get(tour.id).intValue()));
        } else {
            holder.txtNumberofRating.setText("Chưa có đánh giá");
            holder.txtRating.setText("0");
        }

    }

    @Override
    public int getItemCount() {
        return tourList.size();
    }

    @Override
    public void onGetTourImageSuccess(int pos, final String tourId, final Bitmap tourImage) {
        Bitmap thumb = Bitmap.createScaledBitmap(tourImage, photoWidth, photoHeight, false);
        updateImage(tourId, thumb);
        if (externalStoragePermissionGranted) {
            String currentTourPath = new StringBuilder(toursPath).append(tourId).append("/").toString();
            if (!externalStorageInteractor.isExistFile(currentTourPath, tourId + pos))
                externalStorageInteractor.saveBitmapToExternalFile(currentTourPath, tourId + pos, tourImage,50);
        }
    }


    @Override
    public void onLoadImageThumpnailSuccess(String fileName, Bitmap image) {
        updateImage(fileName.substring(0, fileName.length() - 1), image);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtTourName, txtRating, txtTourPrice, txtNumberofRating;
        ImageView imgvTourPhoto;

        ViewHolder(View itemView) {
            super(itemView);
            txtTourName = itemView.findViewById(R.id.txtTourName);
            txtRating = itemView.findViewById(R.id.txtRating);
            txtTourPrice = itemView.findViewById(R.id.txtTourPrice);
            imgvTourPhoto = itemView.findViewById(R.id.imgvTour);
            txtNumberofRating = itemView.findViewById(R.id.txtNumberofRating);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null)
                mClickListener.onTourItemClick(view, tourList.get(getAdapterPosition()).id, tourList.get(getAdapterPosition()).owner);
        }
    }

    public void setClickListener(TourAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onTourItemClick(View view, String tourId, String ownerId);
    }

    public void updateImage(final String tourId, final Bitmap tourImage) {
        parent.post(new Runnable() {
            @Override
            public void run() {
                tourImageMap.put(tourId, tourImage);
                for (int i = 0; i < tourList.size(); i++)
                    if (tourList.get(i).id.equals(tourId))
                        notifyItemChanged(i);
                ;
            }
        });
    }
}
