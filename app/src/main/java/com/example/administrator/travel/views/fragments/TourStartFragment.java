package com.example.administrator.travel.views.fragments;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.travel.R;
import com.example.administrator.travel.models.entities.TourStartDate;
import com.example.administrator.travel.presenters.TourStartPresenter;
import com.example.administrator.travel.views.TourStartView;
import com.example.administrator.travel.views.activities.BookTourActivity;

import java.io.Serializable;
import java.nio.channels.Selector;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TourStartFragment extends Fragment implements TourStartView {
    ListView lstvSelectTour;
    String tourId;
    TourStartPresenter presenter;
    Boolean bookTourClicked;
    public TourStartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tour_start, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lstvSelectTour = getActivity().findViewById(R.id.lstvSelectTour);
        bookTourClicked=false;
        presenter = new TourStartPresenter(this);
        Bundle bundle = getActivity().getIntent().getExtras();
        tourId=bundle.getString("tourId");
        presenter.getTourStartDate(tourId);

    }

    @Override
    public void showTourStartDate(List<TourStartDate> listTourStartDate) {
        TourStartAdapter adapter = new TourStartAdapter(listTourStartDate);
        lstvSelectTour.setAdapter(adapter);
        Log.e("SelectTourFragment: ", "showTourStartDate");
    }

    @Override
    public void onResume() {
        super.onResume();
        if(bookTourClicked) {
            presenter.getTourStartDate(tourId);
            bookTourClicked=false;
        }
    }

    public class TourStartAdapter extends BaseAdapter {
        List<TourStartDate> list;
        Button btnBookTour;
        TextView txtStartDay, txtNumberPeople;
        public TourStartAdapter(List<TourStartDate> list)
        {
            if(list!=null) {
                this.list = list;
            }
            else
                this.list=new ArrayList<>();
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
        public View getView(final int position, View convertView, ViewGroup parent) {

                convertView = getActivity().getLayoutInflater().inflate(R.layout.tour_start_date_item, null);
            txtStartDay = convertView.findViewById(R.id.txtStart);
            txtNumberPeople = convertView.findViewById(R.id.txtNumberPeople);
            btnBookTour=convertView.findViewById(R.id.btnBookTour);

            Date date = new Date(list.get(position).startDate);
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

            txtStartDay.setText(dateFormat.format(date));

            txtNumberPeople.setText(list.get(position).peopleBooking+"");
            btnBookTour.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bookTourClicked=true;
                    Intent intent = new Intent(getActivity(), BookTourActivity.class);
                    int pos = position;
                    intent.putExtra("tour", (Serializable) getItem(pos));
                    intent.putExtra("tourId", tourId);
                    startActivity(intent);
                }
            });
            return convertView;
        }
    }

    //    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        Log.e("SelectTourFragment", "onDestroy: " );
//    }
}
