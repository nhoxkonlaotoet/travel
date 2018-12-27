package com.example.administrator.travel.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.administrator.travel.R;

/**
 * Created by Administrator on 24/12/2018.
 */

public class SlideTourImageAdapter extends PagerAdapter {
    LayoutInflater layoutInflater;
    Bitmap[] images;
    Integer n;
    Context context;
    ImageView imgv;

    public SlideTourImageAdapter(Bitmap[] images,Integer numberofImages,Context context)
    {
        this.images=images;
        this.context=context;
        n=numberofImages;
    }
    @Override
    public int getCount() {
        return n;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (RelativeLayout) object;
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_tour_image, null);
        imgv=view.findViewById(R.id.imgvTourImage);
        Log.e( "instantiateItem: ",position+"   "+(images[position]==null) );
        imgv.setImageBitmap(images[position]);
        Log.e("pos",position+"");
        container.addView(view);
        return view;
    }
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout)object);
    }
}