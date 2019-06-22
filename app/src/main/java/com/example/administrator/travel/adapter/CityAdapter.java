package com.example.administrator.travel.adapter;

import android.Manifest;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v4.content.ContextCompat;
import android.content.pm.PackageManager;

import com.example.administrator.travel.R;
import com.example.administrator.travel.models.bases.CityInteractor;
import com.example.administrator.travel.models.bases.ExternalStorageInteractor;
import com.example.administrator.travel.models.entities.City;
import com.example.administrator.travel.models.impls.CityInteractorImpl;
import com.example.administrator.travel.models.impls.ExternalStorageInteractorImpl;
import com.example.administrator.travel.models.listeners.Listener;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Admin on 5/30/2019.
 */

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder>
        implements Listener.OnLoadCityPhotoFinishedListener, Listener.OnLoadImageThumpnailFinishedListener {
    private LayoutInflater mInflater;
    private RecyclerView parent;
    private CityAdapter.CityClickListener mClickListener;
    private List<City> cityList;
    private HashMap<String, Bitmap> cityPhotoMap;

    private CityInteractor cityInteractor;
    private ExternalStorageInteractor externalStorageInteractor;

    private String citiesPath;
    private boolean externalStoragePermissionGranted;
    private boolean[] loadPhotoFlags;
    private int clickPos = -1;
    private int colorCreme, colorLicoriceDark;
    private int photoWidth, photoHeight;
    public CityAdapter(Context context, List<City> cityList) {
        if (context == null)
            return;
        this.mInflater = LayoutInflater.from(context);
        this.cityList = cityList;
        cityPhotoMap = new HashMap<>();
        loadPhotoFlags = new boolean[cityList.size()];
        cityInteractor = new CityInteractorImpl();
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            externalStoragePermissionGranted = true;
            externalStorageInteractor = new ExternalStorageInteractorImpl();
            citiesPath = context.getString(R.string.external_storage_path_cities);
        }
        colorCreme = ContextCompat.getColor(context, R.color.colorCreme);
        colorLicoriceDark = ContextCompat.getColor(context, R.color.colorLicoriceDark);
        photoWidth = (int)context.getResources().getDimension(R.dimen.city_image_thumpnail_width);
        photoHeight = (int)context.getResources().getDimension(R.dimen.city_image_thumpnail_height);

    }

    @Override
    public CityAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mInflater == null)
            return null;
        View view = mInflater.inflate(R.layout.item_city, parent, false);
        return new CityAdapter.ViewHolder(view);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        parent = recyclerView;
    }

    @Override
    public void onBindViewHolder(CityAdapter.ViewHolder holder, int position) {
        City city = cityList.get(position);
        if (!loadPhotoFlags[position]) {
            loadPhotoFlags[position] = true;
            if (externalStoragePermissionGranted && externalStorageInteractor.isExistFile(citiesPath, city.id)) {
                externalStorageInteractor.getBitmapThumpnailFromExternalFile(citiesPath, city.id, this);
            } else {
                cityInteractor.getCityPhoto(city.id, this);
            }
        }

        if (cityPhotoMap.get(city.id) != null)
            holder.imgvCity.setImageBitmap(cityPhotoMap.get(city.id));
        else
            holder.imgvCity.setImageBitmap(null);
        holder.txtCityName.setText(city.name);

        if (position == clickPos) {
            holder.txtCityName.setTextColor(colorCreme);
            holder.txtCityName.setBackgroundResource(R.drawable.background_border_topright_banana);
        } else {
            holder.txtCityName.setBackgroundResource(R.drawable.background_border_topright_creme);
            holder.txtCityName.setTextColor(colorLicoriceDark);
        }
    }


    @Override
    public int getItemCount() {
        return cityList.size();
    }

    @Override
    public void onLoadCityPhotoSuccess(String cityId, Bitmap cityPhoto) {
        Bitmap thumb = Bitmap.createScaledBitmap(cityPhoto, photoWidth, photoHeight, false);
        updateCityPhoto(cityId, thumb);
        if (externalStoragePermissionGranted)
            if (!externalStorageInteractor.isExistFile(citiesPath, cityId))
                externalStorageInteractor.saveBitmapToExternalFile(citiesPath, cityId, cityPhoto,100);
    }

    private void updateCityPhoto(final String cityId, final Bitmap photo) {
        cityPhotoMap.put(cityId, photo);
        parent.post(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onLoadImageThumpnailSuccess(String fileName, Bitmap image) {
        // filename = cityId
        updateCityPhoto(fileName, image);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgvCity;
        TextView txtCityName;

        public ViewHolder(View itemView) {
            super(itemView);
            txtCityName = itemView.findViewById(R.id.txtCityName);
            imgvCity = itemView.findViewById(R.id.imgvCity);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) {
                if (getAdapterPosition() == clickPos)
                    return;
                clickPos = getAdapterPosition();
                parent.post(new Runnable() {
                    @Override
                    public void run() {
                        notifyDataSetChanged();
                    }
                });
                mClickListener.onItemCityClick(view, cityList.get(getAdapterPosition()).id);

            }
        }
    }

    public void setClickListener(CityAdapter.CityClickListener cityClickListener) {
        this.mClickListener = cityClickListener;
    }

    public interface CityClickListener {
        void onItemCityClick(View view, String cityId);
    }

}
