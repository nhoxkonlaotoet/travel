package com.example.administrator.travel.adapter;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Geocoder;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.models.bases.CompanyInteractor;
import com.example.administrator.travel.models.bases.ExternalStorageInteractor;
import com.example.administrator.travel.models.entities.Company;
import com.example.administrator.travel.models.impls.CompanyInteractorImpl;
import com.example.administrator.travel.models.impls.ExternalStorageInteractorImpl;
import com.example.administrator.travel.models.listeners.Listener;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by Admin on 6/3/2019.
 */

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.ViewHolder> implements Listener.OnLoadImageFinishedListener, Listener.OnGetCompanyLogoFinishedListener {
    private LayoutInflater mInflater;
    private RecyclerView parent;
    private CompanyAdapter.CompanyClickListener mClickListener;

    private ExternalStorageInteractor externalStorageInteractor;
    private CompanyInteractor companyInteractor;

    private List<Company> companyList;
    private HashMap<String, Bitmap> companyPhotoMap;
    private boolean[] loadPhotoFlags;
    private int clickPos = -1;
    private boolean externalStoragePermissionGranted;
    private String companiesPath;

    private Geocoder geocoder;

    public CompanyAdapter(Context context, List<Company> companyList) {
        if (context == null)
            return;
        this.mInflater = LayoutInflater.from(context);
        this.companyList = companyList;
        companyPhotoMap = new HashMap<>();
        loadPhotoFlags = new boolean[companyList.size()];
        companyInteractor = new CompanyInteractorImpl();
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            externalStoragePermissionGranted = true;
            externalStorageInteractor = new ExternalStorageInteractorImpl();
            companiesPath = context.getString(R.string.external_storage_path_companies);
        }
        geocoder = new Geocoder(context, Locale.getDefault());

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mInflater == null)
            return null;
        View view = mInflater.inflate(R.layout.item_company, parent, false);
        return new CompanyAdapter.ViewHolder(view);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        parent = recyclerView;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Company company = companyList.get(position);
        if (!loadPhotoFlags[position]) {
            loadPhotoFlags[position] = true;
            if (externalStoragePermissionGranted && externalStorageInteractor.isExistFile(companiesPath, company.id)) {
                externalStorageInteractor.getBitmapFromExternalFile(companiesPath, company.id, this);
            } else {
                companyInteractor.getCompanyPhoto(company.id, this);
            }
        }
        if (companyPhotoMap.get(company.id) != null)
            holder.imgvCompanyLogo.setImageBitmap(companyPhotoMap.get(company.id));
        else
            holder.imgvCompanyLogo.setImageBitmap(null);
        holder.txtCompanyShortName.setText(company.shortName);


    }

    @Override
    public int getItemCount() {
        return companyList.size();
    }

    @Override //external storage load
    public void onLoadImageSuccess(String fileName, Bitmap image) {
        //filename=companyId
        updateLogo(fileName,image);
    }

    @Override //firebase load
    public void onGetCompanyLogoSuccess(String companyId, Bitmap companyLogo) {
        updateLogo(companyId,companyLogo);
        if (externalStoragePermissionGranted)
            if (!externalStorageInteractor.isExistFile(companiesPath, companyId))
                externalStorageInteractor.saveBitmapToExternalFile(companiesPath, companyId, companyLogo,100);
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgvCompanyLogo;
        TextView txtCompanyShortName;

        public ViewHolder(View itemView) {
            super(itemView);
            imgvCompanyLogo = itemView.findViewById(R.id.imgvCompanyLogo);
            txtCompanyShortName = itemView.findViewById(R.id.txtCompanyShortName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) {
                mClickListener.onItemCompanyClick(view, companyList.get(getAdapterPosition()).id);

            }
        }
    }

    public void setClickListener(CompanyAdapter.CompanyClickListener companyClickListener) {
        this.mClickListener = companyClickListener;
    }

    public interface CompanyClickListener {
        void onItemCompanyClick(View view, String companyId);
    }

    private void updateLogo(final String companyId, final Bitmap companyLogo){
        parent.post(new Runnable() {
            @Override
            public void run() {
                companyPhotoMap.put(companyId,companyLogo);
                notifyDataSetChanged();
            }
        });
    }


}
