package com.example.administrator.travel.views.fragments;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
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
import com.example.administrator.travel.models.DownLoadImageTask;
import com.example.administrator.travel.models.OnTransmitMyLocationFinishedListener;
import com.example.administrator.travel.models.entities.Nearby;
import com.example.administrator.travel.models.entities.NearbyType;
import com.example.administrator.travel.presenters.NearbyPresenter;
import com.example.administrator.travel.views.NearbyView;
import com.example.administrator.travel.views.activities.MapsActivity;
import com.example.administrator.travel.views.activities.ScanQRActivity;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlacePhotoMetadataResponse;
import com.google.android.gms.tasks.Task;

import java.io.Serializable;
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
    List<Nearby> lstNearby=new ArrayList<>();
    List<NearbyType> lstPlacetype= new ArrayList<>();

    NearbyAdapter adapter;
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
        setOnSelectItemSpinner();
        setLstvNearbyItemClick();
        setLstvNearbyScroll();
        presenter.onViewLoad();

    }

    void setLstvNearbyScroll(){
        lstvNearby.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                if (i == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                        && (lstvNearby.getLastVisiblePosition() - lstvNearby.getHeaderViewsCount() -
                        lstvNearby.getFooterViewsCount()) >= (adapter.getCount() - 1)) {
                    presenter.onListViewNearbyScrollBottom();
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });
    }
    void setLstvNearbyItemClick(){
        lstvNearby.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), MapsActivity.class);
                intent.putExtra("action","nearby");
                String destination = view.getTag().toString();
                intent.putExtra("destination", destination);
                startActivity(intent);
            }
        });
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
        this.lstNearby=lstNearby;
        adapter=new NearbyAdapter(context, lstNearby, mylocation);
        lstvNearby.setAdapter(adapter);

    }

    @Override
    public void appendNearbys(List<Nearby> lstNearby) {
        for(int i=0;i<lstNearby.size();i++)
            adapter.lstNearby.add(lstNearby.get(i));
        Log.e( "appendNearbys: ", adapter.lstNearby.size()+"");
        adapter.notifyDataSetChanged();

    }

    @Override
    public void notifyGetNearbyFailure(Exception ex) {

    }

    @Override
    public void updateListViewImages(int index, Bitmap bitmap) {
     if(index<adapter.lstNearby.size())
         adapter.lstNearby.get(index).photo=bitmap;
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDataChanged(Location data) {
        Log.e("fragment receive: ",data+"" );
        presenter.onViewReceivedLocation(data);


    }



}
