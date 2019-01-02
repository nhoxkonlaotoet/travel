package com.example.administrator.travel.views.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.travel.R;
import com.example.administrator.travel.adapter.CustomRecyclerContactAdapter;
import com.example.administrator.travel.presenters.ChatContactFriendPresenter;
import com.example.administrator.travel.presenters.ChatContactFriendPresenterImpl;
import com.example.administrator.travel.views.activities.ChatMessagerActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Henry
 */

public class ChatContactFriendFragment extends Fragment implements ChatContactFriendView{

    SharedPreferences sharedPreferences;
    RecyclerView mContactFriends;
    List<String> listKeyFriends = new ArrayList<>();
    List<String> listUserGroup = new ArrayList<>();

    CustomRecyclerContactAdapter adapter;
    ChatContactFriendPresenter chatContactFriendPresenter;

    public ChatContactFriendFragment() {
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
        View view = inflater.inflate(R.layout.fragment_chat_contact_friend, container, false);
//        setWidgets
        mContactFriends = (RecyclerView) view.findViewById(R.id.list_chat_contact_friend);
//        If the size of views will not change as the data changes.
        mContactFriends.setHasFixedSize(true);
//        set adapter
        adapter = new CustomRecyclerContactAdapter(listKeyFriends);
        mContactFriends.setAdapter(adapter);
        mContactFriends.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter.notifyDataSetChanged();
//       presenter
        chatContactFriendPresenter = new ChatContactFriendPresenterImpl(this);

//       sharedPreferences
        sharedPreferences = getActivity().getSharedPreferences("dataLogin",android.content.Context.MODE_PRIVATE);

        chatContactFriendPresenter.getFriendsPresenter(sharedPreferences);


        adapter.setOnItemClickListener(new CustomRecyclerContactAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String username, int index) {
//                chuyen qua man hinh chat
//                ChatMessagerFragment chatMessagerFragment = new ChatMessagerFragment();
//                Bundle bundle = new Bundle();
//                bundle.putString("UserName",username);
//                bundle.putString("KeyUserGroup",listUserGroup.get(index));
//                chatMessagerFragment.setArguments(bundle);
//                android.app.FragmentManager manager1 = getFragmentManager();
//                manager1.beginTransaction().replace(R.id.conten_layout_chatmanager, chatMessagerFragment,
//                        chatMessagerFragment.getTag())
//                        .addToBackStack("ChatMessager").commit();

                startActivity(new Intent(getActivity(), ChatMessagerActivity.class)
                        .putExtra("UserName",username)
                        .putExtra("KeyUserGroup",listUserGroup.get(index)));
            }
        });


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

    @Override
    public void onResultGetFriends(List<String> listKeyFrs, List<String> listUserGr) {
        listKeyFriends.clear();
        listUserGroup.clear();
        for (int i = 0; i < listKeyFrs.size(); ++i) {
            listKeyFriends.add(listKeyFrs.get(i));
            listUserGroup.add(listUserGr.get(i));
            adapter.notifyDataSetChanged();
        }
    }
}
