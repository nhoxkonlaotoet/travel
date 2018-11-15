package com.example.administrator.travel.views.fragments;


import android.app.Dialog;
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

import java.nio.channels.Selector;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TourStartFragment extends Fragment implements TourStartView {
    ListView lstvSelectTour;
    TourStartPresenter presenter;
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
        presenter = new TourStartPresenter(this);
        Bundle bundle = getActivity().getIntent().getExtras();
       String tourId=bundle.getString("tourId");
        Toast.makeText(getActivity(), tourId, Toast.LENGTH_LONG).show();
        presenter.getTourStartDate(tourId);

    }

    @Override
    public void showTourStartDate(List<TourStartDate> listTourStartDate) {
        BookTourAdapter adapter = new BookTourAdapter(listTourStartDate);
        lstvSelectTour.setAdapter(adapter);
        Log.e("SelectTourFragment: ", "showTourStartDate");
    }

    public class BookTourAdapter extends BaseAdapter {
        List<TourStartDate> list;
        Button btnBookTour;
        TextView txtStartDay, txtNumberPeople;
        public BookTourAdapter(List<TourStartDate> list)
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
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

                convertView = getActivity().getLayoutInflater().inflate(R.layout.tour_start_date_item, null);
            txtStartDay = convertView.findViewById(R.id.txtStart);
            txtNumberPeople = convertView.findViewById(R.id.txtNumberPeople);
            btnBookTour=convertView.findViewById(R.id.btnBookTour);


            txtStartDay.setText(list.get(position).startDate);
            txtNumberPeople.setText(list.get(position).peopleBooking+"");
            btnBookTour.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Dialog dialog = new Dialog(getActivity());
                    dialog.setTitle("Đặt tour");
                    dialog.setContentView(R.layout.dialog_book_tour);
                    dialog.show();
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
