package com.example.administrator.travel.views.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.administrator.travel.NonScrollListView;
import com.example.administrator.travel.R;


public class StatusCommunicationFragment extends Fragment {
    NonScrollListView lstvStatus;
    public StatusCommunicationFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lstvStatus = getActivity().findViewById(R.id.lstvStatus);
        lstvStatus.setAdapter(new StatusAdapter());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_status_communication, container, false);
    }




    public class StatusAdapter extends BaseAdapter {


        public StatusAdapter()
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

            convertView = getActivity().getLayoutInflater().inflate(R.layout.status_item, null);
            //  TextView txt = convertView.findViewById(R.id.txtTourName);
            // txt.setText(list.get(position));


            return convertView;
        }
    }
}
