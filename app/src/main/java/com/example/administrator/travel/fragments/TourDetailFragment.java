package com.example.administrator.travel.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.travel.NonScrollListView;
import com.example.administrator.travel.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class TourDetailFragment extends Fragment {
    NonScrollListView lstvSchedule;
    public TourDetailFragment() {    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tour_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lstvSchedule = getActivity().findViewById(R.id.lstvSchedule);


            ScheduleAdapter adapter = new ScheduleAdapter();
           lstvSchedule.setAdapter(adapter);
//        final List<String[]> values = new LinkedList<String[]>();
//        values.add(new String[]{"Title 1", "Subtitle 1"});
//        values.add(new String[]{"Title 2", "Subtitle 2"});
//        values.add(new String[]{"Title 3", "Subtitle 3"});
//        values.add(new String[]{"Title 4", "Subtitle 4"});
//        values.add(new String[]{"Title 5", "Subtitle 5"});
//        values.add(new String[]{"Title 6", "Subtitle 6"});
//        values.add(new String[]{"Title 7", "Subtitle 7"});
//        values.add(new String[]{"Title 8", "Subtitle 8"});
//
//        lstvSchedule.setAdapter(new ArrayAdapter<String[]>(getActivity(), android.R.layout.simple_expandable_list_item_2, android.R.id.text1, values) {
//
//            @Override
//            public View getView(int position, View convertView, ViewGroup parent) {
//                View view = super.getView(position, convertView, parent);
//
//                return view;
//            }
//        });
    }

    public class ScheduleAdapter extends BaseAdapter {


        public ScheduleAdapter()
        {


        }
        @Override
        public int getCount() {
            return 10;
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

            convertView = getActivity().getLayoutInflater().inflate(R.layout.schedule_tour_item, null);

            if(position==0) {
                View vAboveLine = convertView.findViewById(R.id.vAboveLine);
                vAboveLine.setVisibility(View.INVISIBLE);
            }
            if(position==getCount()-1) {
                View vBelowLine = convertView.findViewById(R.id.vBelowLine);
                vBelowLine.setVisibility(View.INVISIBLE);
            }
            return convertView;
        }
    }
}
