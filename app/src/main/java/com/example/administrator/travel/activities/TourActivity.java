package com.example.administrator.travel.activities;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.activities.TourActivity;
public class TourActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewPager vpTourImage;
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour);

        toolbar = findViewById(R.id.toolbar);
        vpTourImage = findViewById(R.id.vpTourImage);
        txt = findViewById(R.id.txtTourImage);
        toolbar.bringToFront();

        setSupportActionBar(toolbar);
        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        SlideTourImageAdapter adapter = new SlideTourImageAdapter(this);
        vpTourImage.setAdapter(adapter);



    }

    void setSize(View view, int height)
    {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if(height>0)
            params.height -=height;
        view.setLayoutParams(params);

    }



    public class SlideTourImageAdapter extends PagerAdapter {
        LayoutInflater layoutInflater;
        Context context;
        ImageView imgv;
        TextView txt;
        public SlideTourImageAdapter(Context context)
        {
            this.context=context;
        }
        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == (RelativeLayout) object;
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.slide_tour_image, null);

            // imgv=container.findViewById(R.id.imgvTourImage);
            txt = view.findViewById(R.id.txtTourImage);
            Log.e("pos",position+"");
            txt.setText(position+"");

            container.addView(view);
            return view;
        }
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((RelativeLayout)object);
        }
    }

}
