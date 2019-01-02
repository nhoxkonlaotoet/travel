package com.example.administrator.travel.views.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.travel.R;
import com.example.administrator.travel.adapter.CustomRecyclerMessageAdapter;
import com.example.administrator.travel.models.entities.chat.Message;
import com.example.administrator.travel.presenters.ChatMessagerPresenter;
import com.example.administrator.travel.presenters.ChatMessagerPresenterImpl;
import com.example.administrator.travel.views.ChatMessagerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Henry
 */

public class ChatMessagerFragment extends Fragment implements ChatMessagerView{


    RecyclerView mRecyclerView;

    Button btnSendMessage, btnCamera, btnRecord;
    EditText edtMessage;
    TextView tvNameFriend;
    List<Message> listMessages = new ArrayList<>();
    CustomRecyclerMessageAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    SharedPreferences sharedPreferences;
    String AuthID;

    ChatMessagerPresenter chatMessagerPresenter;

    public ChatMessagerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String mKeyGroupChat = getArguments().getString("KeyUserGroup");
            sharedPreferences = getActivity().getSharedPreferences("dataLogin",android.content.Context.MODE_PRIVATE);

            AuthID = sharedPreferences.getString("AuthID","none");

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("KeyGroupChat", mKeyGroupChat);
            editor.commit();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment, getArguments
        View view = inflater.inflate(R.layout.fragment_chat_messager, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.reyclerview_chat_message_list);
        edtMessage = (EditText) view.findViewById(R.id.edt_chat_message_chatbox);
        tvNameFriend = (TextView) view.findViewById(R.id.tv_chat_message_name_friend);
        btnSendMessage = (Button) view.findViewById(R.id.btn_chat_message_send);
        btnCamera = (Button) view.findViewById(R.id.btn_chat_message_camera);
        btnRecord = (Button) view.findViewById(R.id.btn_chat_message_record);


        tvNameFriend.setText(getArguments().getString("UserName"));

        layoutManager = new LinearLayoutManager(getActivity());

        adapter = new CustomRecyclerMessageAdapter(listMessages,AuthID);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(layoutManager);
        adapter.notifyDataSetChanged();

        layoutManager.scrollToPosition(adapter.getItemCount());

//        getMessage
        chatMessagerPresenter = new ChatMessagerPresenterImpl(this);
        sharedPreferences = getActivity().getSharedPreferences("dataLogin",android.content.Context.MODE_PRIVATE);

        chatMessagerPresenter.getMessagePresenter(sharedPreferences);

        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!edtMessage.getText().toString().trim().equals(""))
                    chatMessagerPresenter.SendMessagePresenter(sharedPreferences,edtMessage.getText().toString());
            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Camera click", Toast.LENGTH_SHORT).show();
            }
        });

        btnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Record click", Toast.LENGTH_SHORT).show();
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
    public void showNewMessage(Message chatNew) {
        listMessages.add(chatNew);
        edtMessage.setText("");

        adapter.notifyDataSetChanged();
        adapter.notifyItemChanged(adapter.getItemCount() - 1);

        mRecyclerView.scrollToPosition(adapter.getItemCount() - 1);
    }
}
