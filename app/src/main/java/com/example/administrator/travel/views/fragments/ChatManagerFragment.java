package com.example.administrator.travel.views.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.travel.R;

public class ChatManagerFragment extends Fragment {

    public ChatManagerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat_manager, container, false);

        BottomNavigationView navigation = (BottomNavigationView) view.findViewById(R.id.navigation_chatmanager);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_chat_group);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }

    Integer i=-1;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_chat_group:
                            if(i==0)
                                return false;
                            i=0;
                            ChatFragment chatFragment = new ChatFragment();
                            android.app.FragmentManager manager= getFragmentManager();
                            manager.beginTransaction().replace(R.id.conten_layout_chatmanager,chatFragment,
                                    chatFragment.getTag()).commit();
                            return true;

                        case R.id.navigation_chat_listfriends:
                            if(i==1)
                                return false;
                            i=1;
                            ChatListFriendFragment chatListFriendFragment = new ChatListFriendFragment();
                            android.app.FragmentManager manager1 = getFragmentManager();
                            manager1.beginTransaction().replace(R.id.conten_layout_chatmanager,chatListFriendFragment,
                                    chatListFriendFragment.getTag()).commit();
                            return true;
                        case R.id.navigation_chat_timeline:
                            if(i==2)
                                return false;
                            i=2;
                            return true;
                        case R.id.navigation_chat_setting:
                            if(i==3)
                                return false;
                            i=3;
                            return true;
                    }
                    return false;
                }
    };
}
