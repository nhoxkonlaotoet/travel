package com.example.administrator.travel.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.activities.TourActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFeedFragment extends Fragment {


    public NewsFeedFragment() {
        // Required empty public constructor
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
        ListView lstv = getActivity().findViewById(R.id.lstvNewsFeed);
        NewsFeedAdapter a = new NewsFeedAdapter();
        lstv.setAdapter(a);
        lstv.setSelector(R.color.transparent);
        lstv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), TourActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
