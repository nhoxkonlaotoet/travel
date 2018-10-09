package com.example.administrator.travel.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.travel.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StatusCommunicationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StatusCommunicationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatusCommunicationFragment extends Fragment {

    public StatusCommunicationFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_status_communication, container, false);
    }





}
