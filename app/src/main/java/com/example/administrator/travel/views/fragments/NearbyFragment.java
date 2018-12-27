package com.example.administrator.travel.views.fragments;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.adapter.NearbyAdapter;
import com.example.administrator.travel.models.OnTransmitMyLocationFinishedListener;
import com.example.administrator.travel.models.entities.Nearby;
import com.example.administrator.travel.models.entities.NearbyType;
import com.example.administrator.travel.presenters.NearbyPresenter;
import com.example.administrator.travel.views.NearbyView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NearbyFragment extends Fragment implements NearbyView, OnTransmitMyLocationFinishedListener {

    Context context;
    ListView lstvNearby;
    Spinner spinnerPlaceType;
    NearbyPresenter presenter = new NearbyPresenter(this);
    Boolean firstLoad = true;
    SeekBar seekbarRadius;
    TextView txtRadius;
    Button btnOption,btnSearch;
    LinearLayout layoutOption;
    List<NearbyType> lstPlacetype= new ArrayList<>();
    public NearbyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        context=getActivity();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nearby, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lstvNearby = getActivity().findViewById(R.id.lstvNearby);
        spinnerPlaceType=getActivity().findViewById(R.id.spinnerPlaceType);
        seekbarRadius = getActivity().findViewById(R.id.seekBarRadius);
        txtRadius = getActivity().findViewById(R.id.txtRadius);
        btnOption = getActivity().findViewById(R.id.btnOption);
        btnSearch = getActivity().findViewById(R.id.btnSearch);
        layoutOption = getActivity().findViewById(R.id.layoutOption);

        setOnSelectItemSpinner();
        setOnChangeSeekbarRadius();
        seekbarRadius.setProgress(5);

        setBtnOptionClick();
        setBtnSearchClick();
        presenter.onViewLoad();

    }



    void setOnSelectItemSpinner()
    {
        spinnerPlaceType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                presenter.onSelectItemSpinnerPlaceType(lstPlacetype.get(position).value, getActivity().getString(R.string.google_maps_key));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }
    void setOnChangeSeekbarRadius(){
        seekbarRadius.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                presenter.onSeekbarRadiusChanged(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
    void setBtnOptionClick(){
        btnOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onBtnOptionClicked();
            }
        });
    }
    void setBtnSearchClick(){
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onBtnSearchClicked();
            }
        });
    }
    @Override
    public void setTextRadius(String text) {
        txtRadius.setText(text);
    }

    @Override
    public void showLayoutOption() {
        layoutOption.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLayoutOption() {
        layoutOption.setVisibility(View.GONE);
    }

    @Override
    public void showPlacetypes(List<NearbyType> lstPlacetype) {
        this.lstPlacetype=lstPlacetype;
        List<String> lstSpinnerPlacetype = new ArrayList<>();
        int n=lstPlacetype.size();
        for(int i=0;i<n;i++)
            lstSpinnerPlacetype.add(lstPlacetype.get(i).display);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, lstSpinnerPlacetype);
        spinnerPlaceType.setAdapter(adapter);
    }

    @Override
    public void showNearbys(List<Nearby> lstNearby, Location mylocation) {
      //  Log.e("count nearby: ", lstNearby.size()+"");
        lstvNearby.setAdapter(new NearbyAdapter(context, lstNearby, mylocation));
    }

    @Override
    public void notifyGetNearbyFailure(Exception ex) {

    }

    @Override
    public void onDataChanged(Location data) {
        Log.e("fragment receive: ",data+"" );
        presenter.onViewReceivedLocation(data);


    }



}
