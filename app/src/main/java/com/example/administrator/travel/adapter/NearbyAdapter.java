package com.example.administrator.travel.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.models.entities.Nearby;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 24/12/2018.
 */

public class NearbyAdapter extends BaseAdapter {
    Context context;
    public List<Nearby> lstNearby = new ArrayList<>();
    Location mylocation;
    String open,close, pricelv0,pricelv1,pricelv2,pricelv3,pricelv4;
    int idhalfstar,idnostar,idclose;

    public NearbyAdapter(Context context, List<Nearby> lstNearby, Location mylocation)
    {
        this.context=context;
        this.lstNearby=lstNearby;
        this.mylocation=mylocation;
        open="Mở cửa";
        close="Đóng cửa";
        pricelv0 = "Miễn phí";
        pricelv1 = "Rẻ";
        pricelv2 = "Vừa phải";
        pricelv3 = "Đắt";
        pricelv4 = "Rất đắt";
        idhalfstar = R.drawable.ic_half_star_yellow_24dp;
        idnostar = R.drawable.ic_star_gray_24dp;
        idclose = R.drawable.ic_close_door_24dp;
    }

    @Override
    public int getCount() {
        return lstNearby.size();
    }

    @Override
    public Object getItem(int position) {
        return lstNearby.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView =  ((Activity)context).getLayoutInflater().inflate(R.layout.item_nearby, null);
        TextView txtNearbyName = convertView.findViewById(R.id.txtNearbyName);
        TextView txtNearbyDistance = convertView.findViewById(R.id.txtNearbyDistance);
        TextView txtRating =convertView.findViewById(R.id.txtRating);
        ImageView imgvRating = convertView.findViewById(R.id.imgvRating);
        TextView txtOpen = convertView.findViewById(R.id.txtOpen);
        TextView txtPriceLevel = convertView.findViewById(R.id.txtPriceLevel);
        ImageView imgvPriceLevel= convertView.findViewById(R.id.imgvPriceLevel);
        ImageView imgvOpen = convertView.findViewById(R.id.imgvOpen);
        ImageView imgvNearby = convertView.findViewById(R.id.imgvNearby);
        ProgressBar progressbarImageNearby = convertView.findViewById(R.id.progressbarImageNearby);

        Nearby nearby = lstNearby.get(position);

        float[] result = new float[1];
        Location.distanceBetween(mylocation.getLatitude(), mylocation.getLongitude(),
                nearby.location.latitude,nearby.location.longitude, result);

        if(nearby.photo!=null) {
            imgvNearby.setImageBitmap(nearby.photo);
            progressbarImageNearby.setVisibility(View.INVISIBLE);
        }
        txtNearbyName.setText(nearby.name);
        float km,m;
        m=result[0];
        if(m >= 1000) {
            km=((Math.round(m/100))*100);
            km/=1000;
            txtNearbyDistance.setText(+km+"km");
        }
        else {
            txtNearbyDistance.setText(+Math.round(m)+ "m");
        }

        txtRating.setText(nearby.rating.toString());
        if(nearby.rating<4 && nearby.rating>1) {
            imgvRating.setImageResource(idhalfstar);

        }
        else if(nearby.rating<1) {
            imgvRating.setImageResource(idnostar);
        }

        if(nearby.openNow==null) {
            txtOpen.setVisibility(View.INVISIBLE);
            imgvOpen.setVisibility(View.INVISIBLE);
        }
        else if(nearby.openNow ) {
            txtOpen.setText(open);
        }
        else {
            txtOpen.setText(close);
            imgvOpen.setImageResource(idclose);
        }
        switch (nearby.priceLevel){
            case 0:
                txtPriceLevel.setText(pricelv0);
                break;
            case 1:
                txtPriceLevel.setText(pricelv1);
                break;
            case 2:
                txtPriceLevel.setText(pricelv2);
                break;
            case 3:
                txtPriceLevel.setText(pricelv3);
                break;
            case 4:
                txtPriceLevel.setText(pricelv4);
                break;
            default:
                imgvPriceLevel.setVisibility(View.INVISIBLE);
                txtPriceLevel.setVisibility(View.INVISIBLE);
                break;

        }
        return convertView;
    }
}