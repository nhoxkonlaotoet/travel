package com.example.administrator.travel.adapter;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
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
import com.example.administrator.travel.models.entities.TourStartDate;
import com.example.administrator.travel.models.impls.ExternalStorageInteractorImpl;
import com.example.administrator.travel.models.impls.TourInteractorImpl;
import com.example.administrator.travel.models.listeners.Listener;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by Admin on 6/1/2019.
 */

public class TourAdapter extends RecyclerView.Adapter<TourAdapter.ViewHolder> implements Listener.OnGetTourImageFinishedListener, Listener.OnLoadImageFinishedListener {
    private final static String TYPE_LIKED_TOUR = "liked";
    private final static String TYPE_ABOUT_TO_DEPART_TOUR = "aboutToDepart";

    private LayoutInflater mInflater;
    private TourAdapter.ItemClickListener mClickListener;
    private RecyclerView parent;

    private List<Tour> tourList;
    private HashMap<String, Double> ratingMap;
    private HashMap<String, Bitmap> tourImageMap;
    private HashMap<String, TourStartDate> tourStartMap;

    private TourInteractor tourInteractor;
    private ExternalStorageInteractor externalStorageInteractor;

    private String toursPath, itemType;
    private boolean[] loadPhotoFlags;
    private boolean externalStoragePermissionGranted;

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
        itemType = TYPE_LIKED_TOUR;
    }

    public TourAdapter(Context context, HashMap<String, TourStartDate> tourStartMap, List<Tour> tourList) {
        if (context == null)
            return;
        mInflater = LayoutInflater.from(context);
        this.tourList = tourList;
        this.tourStartMap = tourStartMap;
        tourImageMap = new HashMap<>();
        tourInteractor = new TourInteractorImpl();
        loadPhotoFlags = new boolean[tourList.size()];
        initUsingExternalStorage(context);
        itemType = TYPE_ABOUT_TO_DEPART_TOUR;
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
        if (itemType.equals(TYPE_ABOUT_TO_DEPART_TOUR))
            view = mInflater.inflate(R.layout.item_tour_about_to_depart, parent, false);
        else //if(itemType.equals(TYPE_LIKED_TOUR))
            view = mInflater.inflate(R.layout.item_tour, parent, false);

        return new TourAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TourAdapter.ViewHolder holder, int position) {
        Tour tour = tourList.get(position);
        Random rand = new Random();
        int getRandomTourPhoto = rand.nextInt(tour.numberofImages);

        if (!loadPhotoFlags[position]) {
            String currentTourPath = new StringBuilder(toursPath).append(tour.id).append("/").toString();
            if (externalStoragePermissionGranted
                    && externalStorageInteractor.isExistFile(currentTourPath, tour.id + getRandomTourPhoto)) {
                externalStorageInteractor.loadBitmapFromExternalFile(currentTourPath, tour.id + getRandomTourPhoto, this);
                Log.e("fromSDcard0: ", currentTourPath+"    "+tour.id+ " "+getRandomTourPhoto);
            } else {
                tourInteractor.getTourImage(getRandomTourPhoto, tour.id, this);
                Log.e("fromFirebase: ", tour.id+ " "+getRandomTourPhoto);
            }
            loadPhotoFlags[position] = true;
        }
        if (tourImageMap.get(tour.id) != null)
            holder.imgvTourPhoto.setImageBitmap(tourImageMap.get(tour.id));
        holder.txtTourName.setText(tour.name);



        if (itemType.equals(TYPE_ABOUT_TO_DEPART_TOUR)) {
            TourStartDate tourStartDate = tourStartMap.get(tour.id);
            if (tourStartDate.adultPrice < tour.adultPrice) {
                holder.imgvHotSale.setVisibility(View.VISIBLE);
                holder.txtTourPriceOld.setVisibility(View.VISIBLE);
                SpannableString oldPrice = new SpannableString(String.valueOf(tour.adultPrice) + "đ");
                oldPrice.setSpan(new StrikethroughSpan(), 0, oldPrice.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                holder.txtTourPriceOld.setText(oldPrice);
                holder.txtTourPrice.setText(tourStartDate.adultPrice + "đ");

            } else {
                holder.imgvHotSale.setVisibility(View.GONE);
                holder.txtTourPriceOld.setVisibility(View.GONE);
                StringBuilder priceBuilder = new StringBuilder("Chỉ").append(tour.adultPrice).append("đ");
                holder.txtTourPrice.setText(priceBuilder.toString());

            }
            Long timeLeftMillis = tourStartMap.get(tour.id).startDate - 86400000 * 3 - System.currentTimeMillis();
            Long daysLeft = timeLeftMillis / 86400000;
            Long hoursLeft = (timeLeftMillis - daysLeft * 86400000) / 3600000;
            StringBuilder timeLeftBuilder = new StringBuilder()
                    .append(daysLeft)
                    .append(" ngày ")
                    .append(hoursLeft)
                    .append(" giờ");
            holder.txtDaysLeft.setText(timeLeftBuilder.toString());
        }else if(itemType.equals(TYPE_LIKED_TOUR)){
            holder.txtTourPrice.setText(String.valueOf(tour.adultPrice) + "đ");
            if(tour.ratings!=null) {
                holder.txtNumberofRating.setText(String.valueOf(tour.ratings.size()+" lượt đánh giá"));
                holder.txtRating.setText(String.valueOf(ratingMap.get(tour.id).intValue()));
            }
            else
            {
                holder.txtNumberofRating.setText("Chưa có đánh giá");
                holder.txtRating.setText("0");
            }
        }
    }

    @Override
    public int getItemCount() {
        return tourList.size();
    }

    @Override
    public void onGetTourImageSuccess(int pos, final String tourId, final Bitmap tourImage) {
        updateImage(tourId, tourImage);
        if (externalStoragePermissionGranted) {
            String currentTourPath = new StringBuilder(toursPath).append(tourId).append("/").toString();
            if (!externalStorageInteractor.isExistFile(currentTourPath, tourId + pos))
                externalStorageInteractor.saveBitmapToExternalFile(currentTourPath, tourId + pos, tourImage);
        }
    }

    @Override
    public void onLoadImageSuccess(String fileName, Bitmap image) {
        // filename =   IQWEAHDASDHQWEKJHQWJE0
        // tourid =     IQWEAHDASDHQWEKJHQWJE
        // file =                            0
        Log.e("onLoadImageSuccess: ", fileName.substring(0, fileName.length() - 2));
        updateImage(fileName.substring(0, fileName.length() - 1), image);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtTourName, txtRating, txtTourPrice, txtNumberofRating, txtDaysLeft, txtTourPriceOld;
        ImageView imgvTourPhoto, imgvHotSale;

        ViewHolder(View itemView) {
            super(itemView);
            if (itemType.equals(TYPE_LIKED_TOUR)) {
                txtTourName = itemView.findViewById(R.id.txtTourName);
                txtRating = itemView.findViewById(R.id.txtRating);
                txtTourPrice = itemView.findViewById(R.id.txtTourPrice);
                imgvTourPhoto = itemView.findViewById(R.id.imgvTour);
                txtNumberofRating = itemView.findViewById(R.id.txtNumberofRating);
            } else if (itemType.equals(TYPE_ABOUT_TO_DEPART_TOUR)) {
                txtTourName = itemView.findViewById(R.id.txtTourName);
                txtDaysLeft = itemView.findViewById(R.id.txtDaysLeft);
                txtTourPrice = itemView.findViewById(R.id.txtTourPrice);
                imgvTourPhoto = itemView.findViewById(R.id.imgvTour);
                txtTourPriceOld = itemView.findViewById(R.id.txtTourPriceOld);
                imgvHotSale = itemView.findViewById(R.id.imgvHotSale);
            }
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null)
                mClickListener.onTourItemClick(view, tourList.get(getAdapterPosition()).id, tourList.get(getAdapterPosition()).owner);
        }
    }

    public Tour getItem(int pos) {
        return tourList.get(pos);
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
                notifyDataSetChanged();
            }
        });
    }
}
