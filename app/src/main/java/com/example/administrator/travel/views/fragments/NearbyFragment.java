package com.example.administrator.travel.views.fragments;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.administrator.travel.R;
import com.example.administrator.travel.adapter.NearbyAdapter;
import com.example.administrator.travel.models.entities.MyLatLng;
import com.example.administrator.travel.models.entities.NearbyType;
import com.example.administrator.travel.models.entities.place.nearby.Nearby;
import com.example.administrator.travel.presenters.bases.NearbyPresenter;
import com.example.administrator.travel.presenters.impls.NearbyPresenterImpl;
import com.example.administrator.travel.views.bases.NearbyView;
import com.example.administrator.travel.views.activities.MapsActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NearbyFragment extends Fragment implements NearbyView, NearbyAdapter.NearbyClickListener {

    RecyclerView recyclerViewNearby;
    Spinner spinnerPlaceType;
    NearbyPresenter presenter;

    NearbyAdapter adapter;

    public NearbyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nearby, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewNearby = getActivity().findViewById(R.id.recyclerViewNearby);
        spinnerPlaceType = getActivity().findViewById(R.id.spinnerPlaceType);
        setOnSelectItemSpinner();
        //setLstvNearbyItemClick();
        setLstvNearbyScroll();
        presenter = new NearbyPresenterImpl(this);
        presenter.onViewCreated();

    }

    void setLstvNearbyScroll() {
        recyclerViewNearby.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    presenter.onListViewNearbyScrollBottom();
                }
            }
        });
    }

//    void setLstvNearbyItemClick() {
//        recyclerViewNearby.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent(getActivity(), MapsActivity.class);
//                intent.putExtra("action", "nearby");
//                String destination = view.getTag().toString();
//                intent.putExtra("destination", destination);
//                startActivity(intent);
//            }
//        });
//    }

    void setOnSelectItemSpinner() {
        spinnerPlaceType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                presenter.onSelectItemSpinnerPlaceType(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }


    @Override
    public void showPlacetypes(List<NearbyType> lstPlacetype) {
        List<String> lstSpinnerPlacetype = new ArrayList<>();
        int n = lstPlacetype.size();
        for (int i = 0; i < n; i++)
            lstSpinnerPlacetype.add(lstPlacetype.get(i).display);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, lstSpinnerPlacetype);
        spinnerPlaceType.setAdapter(adapter);
    }

    @Override
    public void showNearbys(List<Nearby> lstNearby, MyLatLng mylocation) {
        adapter = new NearbyAdapter(getContext(), lstNearby, mylocation);
        adapter.setClickListener(this);
        recyclerViewNearby.setAdapter(adapter);

    }

    @Override
    public void appendNearbys(List<Nearby> lstNearby) {
        adapter.appendItems(lstNearby);
    }

    @Override
    public void notify(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void updateListViewImages(int index, Bitmap bitmap) {
        adapter.updateImage(index, bitmap);
    }

    @Override
    public void gotoMapActivity(String origin, String destination, String openFrom) {
        Intent intent = new Intent(getActivity(), MapsActivity.class);
        intent.putExtra("openFrom", openFrom);
        intent.putExtra("origin", origin);
        intent.putExtra("destination", destination);
        startActivity(intent);
    }

    @Override
    public Context getContext() {
        return getActivity();
    }

    @Override
    public void onNearbyClick(View view, Nearby nearby) {
        presenter.onNearbyItemClicked(nearby);
    }
}
