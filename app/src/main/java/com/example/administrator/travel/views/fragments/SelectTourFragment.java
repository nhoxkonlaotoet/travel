package com.example.administrator.travel.views.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.travel.R;

import java.nio.channels.Selector;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelectTourFragment extends Fragment {
    ListView lstvSelectTour;
    public SelectTourFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_select_tour, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lstvSelectTour = getActivity().findViewById(R.id.lstvSelectTour);
       SelectTourAdapter adapter = new SelectTourAdapter();
        if(lstvSelectTour.getAdapter()==null)
           lstvSelectTour.setAdapter(adapter);
        Log.e("SelectTourFragment: ", "onViewCreated");

    }

    public class SelectTourAdapter extends BaseAdapter {

        List<String> lstStartDay = new ArrayList<String>();
        List<String> lstNumberPeople = new ArrayList<String>();
        TextView txtStartDay, txtNumberPeople;
        public SelectTourAdapter()
        {
            lstStartDay.add("10/10/2018");
            lstStartDay.add("16/10/2018");
            lstStartDay.add("25/10/2018");
            lstStartDay.add("29/10/2018");
            lstStartDay.add("6/11/2018");
            lstStartDay.add("14/11/2018");
            lstStartDay.add("20/11/2018");
            lstStartDay.add("1/12/2018");
            lstNumberPeople.add("40/50");
            lstNumberPeople.add("35/50");
            lstNumberPeople.add("15/50");
            lstNumberPeople.add("6/50");
            lstNumberPeople.add("0/50");
            lstNumberPeople.add("0/50");
            lstNumberPeople.add("0/50");
            lstNumberPeople.add("0/50");



        }
        @Override
        public int getCount() {
            return lstStartDay.size();
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

                convertView = getActivity().getLayoutInflater().inflate(R.layout.select_tour_item, null);
            txtStartDay = convertView.findViewById(R.id.txtStart);
            txtNumberPeople = convertView.findViewById(R.id.txtNumberPeople);
            txtStartDay.setText(lstStartDay.get(position));
            txtNumberPeople.setText(lstNumberPeople.get(position));

            return convertView;
        }
    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        Log.e("SelectTourFragment", "onDestroy: " );
//    }
}
