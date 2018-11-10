package com.example.administrator.travel.views.fragments;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import com.example.administrator.travel.R;
import com.example.administrator.travel.models.entities.Tour;
import com.example.administrator.travel.presenters.NewFeedPresenter;
import com.example.administrator.travel.presenters.NewFeedPresenterImpl;
import com.example.administrator.travel.views.NewFeedView;
import com.example.administrator.travel.views.activities.TourActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFeedFragment extends Fragment implements NewFeedView{

    private String array_spinner[];
    Spinner spinner;
    ListView lstv;
    private NewFeedPresenter presenter;
    public NewsFeedFragment() {
        // Required empty public constructor
        array_spinner=new String[4];
        array_spinner[0]="Mọi thứ";
        array_spinner[1]="Tour";
        array_spinner[2]="Địa điểm";
        array_spinner[3]="Đánh giá";
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_news_feed, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter= new NewFeedPresenterImpl(this);
        presenter.getTours();
        lstv = getActivity().findViewById(R.id.lstvNewsFeed);
        spinner = getActivity().findViewById(R.id.spinner);

        lstv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), TourActivity.class);
                intent.putExtra("mytour",false);
                  startActivity(intent);

            }
        });
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), R.layout.news_feed_spinner_item,R.id.txtSpinnerOption, array_spinner);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void showTours(List<Tour> listTour) {
        Log.e( "NewFeedView ", "show tours" );
        NewsFeedAdapter a = new NewsFeedAdapter(listTour);
//        for(Tour tour : listTour)
//        {
//            Log.e("showTours: ", tour.toString());
//        }
        lstv.setAdapter(a);
        lstv.setSelector(R.color.transparent);
    }

    //chua su dung
    public class NewsFeedArrayAdapter extends  ArrayAdapter{

        public NewsFeedArrayAdapter(@NonNull Context context, int resource) {
            super(context, resource);
        }
        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            TextView v = (TextView) super.getView(position, convertView, parent);
            if (v == null) {
                v = new TextView(getActivity());
            }
            v.setTextColor(Color.BLACK);
            v.setText(array_spinner[position]);
            return v;
        }

        @Override
        public String getItem(int position) {
            return array_spinner[position];
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;

            if (v == null) {
                LayoutInflater inflater = getActivity().getLayoutInflater();
                v = inflater.inflate(R.layout.news_feed_spinner_item, null);
            }
            TextView lbl = (TextView) v.findViewById(R.id.txtSpinnerOption);
            lbl.setTextColor(Color.BLACK);
            lbl.setText(array_spinner[position]);
            return convertView;
        }
    }

    public class NewsFeedAdapter extends BaseAdapter {
        List<Tour> list= new ArrayList();

        public NewsFeedAdapter(List<Tour> listTour)
        {
            this.list = listTour;
        }
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

                Tour tour = list.get(position);
                convertView = getActivity().getLayoutInflater().inflate(R.layout.tour_item, null);
               //mapping
                TextView txtTourName = convertView.findViewById(R.id.txtTourName);
                TextView txtDays = convertView.findViewById(R.id.txtDays);
                RatingBar ratingBar = convertView.findViewById(R.id.ratebarTourRating);
                TextView txtNumberofRating = convertView.findViewById(R.id.txtNumberofRating);
                TextView txtTourPrice = convertView.findViewById(R.id.txtTourPrice);
                TextView txtTourSaleprice = convertView.findViewById(R.id.txtTourSalePrice);
               //set values
                txtTourName.setText(tour.name);
                txtDays.setText(tour.days+" ngày "+tour.nights+" đêm");
                ratingBar.setRating(tour.rating);
                txtNumberofRating.setText(tour.numberofRating+" bình chọn");
                txtTourPrice.setText(tour.adultPrice + "đ");
                txtTourSaleprice.setVisibility(View.INVISIBLE);

            return convertView;
        }
    }
}
