package com.example.administrator.travel.views.fragments;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.text.style.SuperscriptSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.adapter.NewsFeedAdapter;
import com.example.administrator.travel.models.entities.City;
import com.example.administrator.travel.models.entities.Tour;
import com.example.administrator.travel.presenters.NewFeedPresenter;
import com.example.administrator.travel.views.NewFeedView;
import com.example.administrator.travel.views.activities.TourActivity;


import java.util.ArrayList;
import java.util.List;

public class NewsFeedFragment extends Fragment implements NewFeedView,NewsFeedAdapter.ItemClickListener{
    Context context;
    private String array_spinner[];
    Spinner spinnerOrigin,spinnerDestination;
    RecyclerView lstv;
    private NewFeedPresenter presenter;
    NewsFeedAdapter adapter;
    public NewsFeedFragment() {
        // Required empty public constructor
        array_spinner=new String[4];
        array_spinner[0]="Mọi thứ";
        array_spinner[1]="Tour";
        array_spinner[2]="Địa điểm";
        array_spinner[3]="Đánh giá";
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context=getActivity();

        return inflater.inflate(R.layout.fragment_news_feed, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter= new NewFeedPresenter(this);
        presenter.onViewLoad();
        lstv = getActivity().findViewById(R.id.lstvNewsFeed);
        //lstv.setSelector(R.color.transparent);

        spinnerOrigin = getActivity().findViewById(R.id.spinnerOrigin);
        spinnerDestination = getActivity().findViewById(R.id.spinnerDestination);


       // setItemListviewTourClick();
        setItemSpinnerOriginSelect();
        setItemSpinnerDestinationSelect();
    }
//    void setItemListviewTourClick(){
//        lstv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                String[] s = view.getTag().toString().split(" ");
//                presenter.onItemListViewTourClicked(s[0],s[1]);
//
//            }
//        });
//    }
    void setItemSpinnerOriginSelect(){
        spinnerOrigin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                presenter.onItemSpinnerOriginSelected(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    void setItemSpinnerDestinationSelect(){
        spinnerDestination.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                presenter.onItemSpinnerDestinationSelected(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    @Override
    public void showCities(List<City> lstCity) {
        String arr[] = new String[lstCity.size()+1];
        int n=lstCity.size();
        arr[0]="Không";
        for(int i=1;i<=n;i++)
            arr[i]=lstCity.get(i-1).name;
        ArrayAdapter adapter = new ArrayAdapter(context, R.layout.news_feed_spinner_item,R.id.txtSpinnerOption, arr);
        spinnerOrigin.setAdapter(adapter);

        spinnerDestination.setAdapter(adapter);
    }

    @Override
    public void showTours(List<Tour> listTour) {
        if(context!=null) {
            adapter = new NewsFeedAdapter(context, listTour);
            adapter.setClickListener(this);
            lstv.setAdapter(adapter);
        }
    }

    @Override
    public void updateTourImage(String tourId, Bitmap img) {
        adapter.updateImage(tourId,img);
    }

    @Override
    public void gogotActivityTour(String tourId, String ownerId) {

        Intent intent = new Intent(getActivity(), TourActivity.class);
        intent.putExtra("tourId",tourId);
        intent.putExtra("mytour",false);
        intent.putExtra("owner",ownerId);
        startActivity(intent);
    }

    @Override
    public void onItemClick(View view, String tourId, String ownerId) {
        presenter.onItemListViewTourClicked(tourId,ownerId);
    }

    //chua su dung
//    public class NewsFeedArrayAdapter extends  ArrayAdapter{
//
//        public NewsFeedArrayAdapter(@NonNull Context context, int resource) {
//            super(context, resource);
//        }
//        @Override
//        public View getDropDownView(int position, View convertView, ViewGroup parent) {
//            TextView v = (TextView) super.getView(position, convertView, parent);
//            if (v == null) {
//                v = new TextView(getActivity());
//            }
//            v.setTextColor(Color.BLACK);
//            v.setText(array_spinner[position]);
//            return v;
//        }
//
//        @Override
//        public String getItem(int position) {
//            return array_spinner[position];
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            View v = convertView;
//
//            if (v == null) {
//                LayoutInflater inflater = getActivity().getLayoutInflater();
//                v = inflater.inflate(R.layout.news_feed_spinner_item, null);
//            }
//            TextView lbl = (TextView) v.findViewById(R.id.txtSpinnerOption);
//            lbl.setTextColor(Color.BLACK);
//            lbl.setText(array_spinner[position]);
//            return convertView;
//        }
//    }
}
