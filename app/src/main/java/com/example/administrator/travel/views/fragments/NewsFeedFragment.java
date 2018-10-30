package com.example.administrator.travel.views.fragments;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.views.activities.TourActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFeedFragment extends Fragment {

    private String array_spinner[];
    Spinner spinner;
    ListView lstv;
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
        lstv = getActivity().findViewById(R.id.lstvNewsFeed);
        spinner = getActivity().findViewById(R.id.spinner);
        NewsFeedAdapter a = new NewsFeedAdapter();
        lstv.setAdapter(a);
        lstv.setSelector(R.color.transparent);
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
        List<String> list= new ArrayList();

        public NewsFeedAdapter()
        {
            list.add("a");
            list.add("b");
            list.add("c");
            list.add("d");
            list.add("e");
            list.add("f");
            list.add("g");
            list.add("h");

        }
        @Override
        public int getCount() {
            return list.size()-1;
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
            if(position%1==0) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.tour_item, null);
              //  TextView txt = convertView.findViewById(R.id.txtTourName);
               // txt.setText(list.get(position));
            }
            else
            {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.review_item, null);
               // TextView txt = convertView.findViewById(R.id.txtUserName);
               // txt.setText(list.get(position));
            }
            return convertView;
        }
    }
}
