package com.example.administrator.travel.views.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.travel.R;
import com.example.administrator.travel.views.activities.SearchFriendsActivity;

/**
 * Created by Henry
 */

// danh ba va tim kiem ban be hoan tat
public class ChatListFriendFragment extends Fragment {

    TabLayout tableLayout;
    public ChatListFriendFragment() {
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
        View view = inflater.inflate(R.layout.fragment_chat_list_friend, container, false);

        tableLayout = (TabLayout) view.findViewById(R.id.tablayout_chatlistfriend);

        tableLayout.addTab(tableLayout.newTab().setText("Danh bạ"));
        tableLayout.addTab(tableLayout.newTab().setText("Kết bạn"));

        tableLayout.setSelected(true);
        tableLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                android.app.FragmentManager manager= getFragmentManager();
//                manager.beginTransaction().replace(R.id.conten_layout_chatmanager,chatFragment,
//                        chatFragment.getTag()).commit();
                int position = tab.getPosition();
                if(position == 0){//contact list friends
                    ChatManagerFragment.rechangeFrmLayoutManager();
                    FriendContactFragment contactFriendFragment = new FriendContactFragment();
                    android.app.FragmentManager manager = getFragmentManager();
                    manager.beginTransaction().replace(R.id.conten_layout_chatlistfriend, contactFriendFragment,
                            contactFriendFragment.getTag()).commit();
                }else if(position == 1){// search friends
//                    ChatManagerFragment.changeFrmLayoutManager();
//                    ChatSearchFriendsFragment searchFriendsFragment = new ChatSearchFriendsFragment();
//                    android.app.FragmentManager manager1 = getFragmentManager();
//                    manager1.beginTransaction().replace(R.id.conten_chatmanager_fullscreen, searchFriendsFragment,
//                            searchFriendsFragment.getTag())
//                            .addToBackStack("searchChatListFriends").commit();

                    tableLayout.setSelected(false);
                    tableLayout.setSelected(true);
                    startActivity(new Intent(getActivity(), SearchFriendsActivity.class));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        FriendContactFragment contactFriendFragment = new FriendContactFragment();
        android.app.FragmentManager manager = getFragmentManager();
        manager.beginTransaction().replace(R.id.conten_layout_chatlistfriend, contactFriendFragment,
                contactFriendFragment.getTag()).commit();

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


}
