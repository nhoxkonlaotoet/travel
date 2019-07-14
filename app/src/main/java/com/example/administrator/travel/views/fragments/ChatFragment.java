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
import android.widget.Toast;

import com.example.administrator.travel.R;
import com.example.administrator.travel.adapter.CustomRecyclerChatAdapter;
import com.example.administrator.travel.models.entities.chat.Chats;
import com.example.administrator.travel.presenters.ChatPresenter;
import com.example.administrator.travel.presenters.ChatPresenterImpl;
import com.example.administrator.travel.views.ChatView;
import com.example.administrator.travel.views.activities.ChatMessagerActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatFragment extends Fragment implements ChatView{

    SharedPreferences sharedPreferences;

    RecyclerView mRecyclerView;

    List<Chats> listLastChat = new ArrayList<>();
    List<String> listKeyFriend = new ArrayList<>();
    List<String> listUserGroup = new ArrayList<>();

    CustomRecyclerChatAdapter adapter;
    ChatPresenter chatPresenter;

    public ChatFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        mRecyclerView =  view.findViewById(R.id.recyclerview_list_chat_group_friend);

        adapter = new CustomRecyclerChatAdapter(listLastChat,listKeyFriend);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter.notifyDataSetChanged();
//       presenter
        chatPresenter = new ChatPresenterImpl(this);

//       sharedPreferences
        sharedPreferences = getActivity().getSharedPreferences("dataLogin",android.content.Context.MODE_PRIVATE);

        chatPresenter.getChatPresenter(sharedPreferences);

        adapter.setOnItemClickListener(new CustomRecyclerChatAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String userName, int position) {
//                chuyen qua man hinh chat
//                ChatMessagerFragment chatMessagerFragment = new ChatMessagerFragment();
//                Bundle bundle = new Bundle();
//                bundle.putString("UserName",userName);
//                bundle.putString("KeyUserGroup",listUserGroup.get(position));
//                chatMessagerFragment.setArguments(bundle);
//                android.app.FragmentManager manager1 = getFragmentManager();
//                manager1.beginTransaction().replace(R.id.conten_layout_chatmanager, chatMessagerFragment,
//                        chatMessagerFragment.getTag())
//                        .addToBackStack("ChatMessager").commit();

                startActivity(new Intent(getActivity(), ChatMessagerActivity.class)
                        .putExtra("UserName",userName)
                        .putExtra("KeyUserGroup",listUserGroup.get(position)));
            }
        });

//        get group friend
        return view;
    }


    @Override
    public void onResultGetChatView(List<Chats> lstLastChat, List<String> lstKeyFriend, List<String> lstUserGroup) {
        listKeyFriend.clear();
        listLastChat.clear();
        listUserGroup.clear();
        for (int i = 0; i < lstLastChat.size(); ++i) {
            listLastChat.add(lstLastChat.get(i));
        }
        for (int i = 0; i < lstKeyFriend.size(); ++i) {
            listKeyFriend.add(lstKeyFriend.get(i));
        }
        for (int i = 0; i < lstUserGroup.size(); ++i) {
            listUserGroup.add(lstUserGroup.get(i));
        }
        if(listLastChat.size() == listKeyFriend.size()) {
            adapter.notifyDataSetChanged();
        }
//        Toast.makeText(getActivity(), "Chats "+listLastChat.size()+" friends "+listKeyFriend.size(), Toast.LENGTH_SHORT).show();
    }
}
