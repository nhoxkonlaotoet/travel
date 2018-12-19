package com.example.administrator.travel.views.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.travel.R;
import com.example.administrator.travel.models.entities.MyLatLng;
import com.example.administrator.travel.models.entities.Participant;
import com.example.administrator.travel.views.activities.HomeActivity;
import com.example.administrator.travel.views.activities.ScanQRActivity;
import com.example.administrator.travel.views.activities.TourActivity;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelectMyTourFragment extends Fragment {
    ListView lstvSelectMyTour;
    LinearLayout btnScan;
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
        btnScan = getActivity().findViewById(R.id.btnScan);
        setBtnScanClick();
        lstvSelectMyTour = getActivity().findViewById(R.id.lstvSelectMyTour);
        lstvSelectMyTour.setAdapter(new SelectMyTourAdapter());
        lstvSelectMyTour.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), TourActivity.class);
                intent.putExtra("mytour",true);
                intent.putExtra("tourId","-LQ2GIaiHuH4LmUVogi5");

                startActivity(intent);
            }
        });
    }
    public void setBtnScanClick()
    {
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanQR();
            }
        });
    }

    public void scanQR()
    {
        Intent intent = new Intent(getActivity(), ScanQRActivity.class);
        startActivityForResult(intent,100 );
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 100 && resultCode ==getActivity().RESULT_OK){
            if(data != null){
                final Barcode barcode = data.getParcelableExtra("barcode");
                Toast.makeText(getActivity(), barcode.displayValue, Toast.LENGTH_LONG).show();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference("participants");
                SharedPreferences prefs =getActivity().getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
                String userId = prefs.getString("AuthID","");
                Participant participant = new Participant(userId,barcode.displayValue,false,
                        new MyLatLng(10.849745, 106.772217),1545110670000L);
                ref.child(barcode.displayValue+"+"+userId).setValue(participant);
            }
        }
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
