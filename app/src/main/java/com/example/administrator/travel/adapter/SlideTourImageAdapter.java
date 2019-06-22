package com.example.administrator.travel.adapter;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.administrator.travel.R;
import com.example.administrator.travel.models.bases.ExternalStorageInteractor;
import com.example.administrator.travel.models.bases.TourInteractor;
import com.example.administrator.travel.models.impls.ExternalStorageInteractorImpl;
import com.example.administrator.travel.models.impls.TourInteractorImpl;
import com.example.administrator.travel.models.listeners.Listener;

import java.util.HashMap;
import java.util.Random;

/**
 * Created by Administrator on 24/12/2018.
 */

public class SlideTourImageAdapter extends PagerAdapter implements Listener.OnLoadImageThumpnailFinishedListener, Listener.OnGetTourImageFinishedListener {
    private LayoutInflater layoutInflater;
    private HashMap<String, Bitmap> imagesMap;
    private Integer n;
    private Context context;
    private ImageView imgv;
    private String tourId, toursPath;
    private boolean[] loadPhotoFlags;
    private boolean externalStoragePermissionGranted;
    private TourInteractor tourInteractor;
    private ExternalStorageInteractor externalStorageInteractor;

    public SlideTourImageAdapter(String tourId, Integer numberofImages, Context context) {
        if (context == null)
            return;
        this.context = context;
        this.tourId = tourId;
        n = numberofImages;
        imagesMap = new HashMap<>();
        loadPhotoFlags = new boolean[n];
        initUsingExternalStorage(context);
        externalStorageInteractor = new ExternalStorageInteractorImpl();
        tourInteractor = new TourInteractorImpl();
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
    public int getCount() {
        return n;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (context == null)
            return null;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_tour_image, null);
        imgv = view.findViewById(R.id.imgvTourImage);

        if (!loadPhotoFlags[position]) {
            String currentTourPath = toursPath + tourId + "/";
            if (externalStoragePermissionGranted
                    && externalStorageInteractor.isExistFile(currentTourPath, tourId + position)) {
                externalStorageInteractor.getBitmapThumpnailFromExternalFile(currentTourPath, tourId + position, this);
                Log.e("fromSDcard0: ", currentTourPath + "    " + tourId + " " + position);
            } else {
                tourInteractor.getTourImage(position, tourId, this);
                Log.e("fromFirebase: ", tourId + " " + position);
            }
            loadPhotoFlags[position] = true;
        }

        if (imagesMap.get(tourId + position) != null)
            imgv.setImageBitmap(imagesMap.get(tourId + position));
        container.addView(view);
        return view;
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }

    public void updateImage(String fileName, Bitmap image) {
        Log.e("updateImage: ", "gfhfghg");
        if (imagesMap != null) {
            imagesMap.put(fileName, image);
            notifyDataSetChanged();
        }
    }

    @Override
    public void onLoadImageThumpnailSuccess(String fileName, Bitmap image) {
        updateImage(fileName, image);
    }

    @Override
    public void onGetTourImageSuccess(int pos, String tourId, Bitmap tourImage) {
        updateImage(tourId + pos, tourImage);
        if (externalStoragePermissionGranted) {
            String currentTourPath = new StringBuilder(toursPath).append(tourId).append("/").toString();
            if (!externalStorageInteractor.isExistFile(currentTourPath, tourId + pos))
                externalStorageInteractor.saveBitmapToExternalFile(currentTourPath, tourId + pos, tourImage, 50);
        }
    }
}