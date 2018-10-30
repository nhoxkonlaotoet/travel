package com.example.administrator.travel.views.fragments;


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

import com.example.administrator.travel.R;
import com.example.administrator.travel.views.activities.TourActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelectMyTourFragment extends Fragment {
    ListView lstvSelectMyTour;

    public SelectMyTourFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_select_my_tour, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lstvSelectMyTour = getActivity().findViewById(R.id.lstvSelectMyTour);
        lstvSelectMyTour.setAdapter(new SelectMyTourAdapter());
        lstvSelectMyTour.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), TourActivity.class);
                intent.putExtra("mytour",true);
                startActivity(intent);
            }
        });
    }

    public class SelectMyTourAdapter extends BaseAdapter {


        public SelectMyTourAdapter()
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

                convertView = getActivity().getLayoutInflater().inflate(R.layout.item_select_my_tour, null);
                //  TextView txt = convertView.findViewById(R.id.txtTourName);
                // txt.setText(list.get(position));


            return convertView;
        }
    }
}
