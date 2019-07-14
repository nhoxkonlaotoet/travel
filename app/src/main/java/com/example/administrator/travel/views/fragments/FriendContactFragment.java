package com.example.administrator.travel.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.administrator.travel.R;
import com.example.administrator.travel.adapter.FriendContactAdapter;
import com.example.administrator.travel.presenters.bases.FriendContactPresenter;
import com.example.administrator.travel.presenters.impls.FriendContactPresenterImpl;
import com.example.administrator.travel.views.activities.ChatMessagerActivity;

import java.util.List;

/**
 * Created by Henry
 */

public class FriendContactFragment extends Fragment implements FriendContactView, FriendContactAdapter.OnItemFriendClickListener {

    RecyclerView recyclerViewFriend;
    FriendContactPresenter presenter;

    public FriendContactFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_contact_friend, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapping();
        if (getActivity() != null) {
            Bundle bundle = getActivity().getIntent().getExtras();
            presenter = new FriendContactPresenterImpl(this);
            presenter.onViewCreated(bundle);
        }
    }

    private void mapping() {
        recyclerViewFriend = getActivity().findViewById(R.id.recyclerViewFriend);
    }

    @Override
    public void showFriends(List<String> friendIdList) {
        FriendContactAdapter adapter = new FriendContactAdapter(friendIdList);
        adapter.setOnItemClickListener(this);
        recyclerViewFriend.setAdapter(adapter);
    }

    @Override
    public void gotoChatAcitivty(String friendId) {
        startActivity(new Intent(getActivity(), ChatMessagerActivity.class)
                .putExtra("userId", friendId));
    }

    @Override
    public void notify(String message) {
        if(getActivity()!=null)
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemFriendClick(String friendId) {
        presenter.onItemFriendClick(friendId);
    }
}
