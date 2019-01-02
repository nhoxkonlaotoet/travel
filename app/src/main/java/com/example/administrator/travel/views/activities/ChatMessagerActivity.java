package com.example.administrator.travel.views.activities;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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

public class ChatMessagerActivity  extends AppCompatActivity  implements ChatMessagerView {

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_chat_messager);

        String mKeyGroupChat = getIntent().getStringExtra("KeyUserGroup");
        sharedPreferences = getSharedPreferences("dataLogin",android.content.Context.MODE_PRIVATE);

        AuthID = sharedPreferences.getString("AuthID","none");

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("KeyGroupChat", mKeyGroupChat);
        editor.commit();


        mRecyclerView = (RecyclerView) findViewById(R.id.reyclerview_chat_message_list);
        edtMessage = (EditText) findViewById(R.id.edt_chat_message_chatbox);
        tvNameFriend = (TextView) findViewById(R.id.tv_chat_message_name_friend);
        btnSendMessage = (Button) findViewById(R.id.btn_chat_message_send);
        btnCamera = (Button) findViewById(R.id.btn_chat_message_camera);
        btnRecord = (Button) findViewById(R.id.btn_chat_message_record);


        tvNameFriend.setText(getIntent().getStringExtra("UserName"));

        layoutManager = new LinearLayoutManager(this);

        adapter = new CustomRecyclerMessageAdapter(listMessages,AuthID);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(layoutManager);
        adapter.notifyDataSetChanged();

        layoutManager.scrollToPosition(adapter.getItemCount());

//        getMessage
        chatMessagerPresenter = new ChatMessagerPresenterImpl(this);
        sharedPreferences = getSharedPreferences("dataLogin",android.content.Context.MODE_PRIVATE);

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
                Toast.makeText(ChatMessagerActivity.this, "Camera click", Toast.LENGTH_SHORT).show();
            }
        });

        btnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ChatMessagerActivity.this, "Record click", Toast.LENGTH_SHORT).show();
            }
        });
    }//end onCreate


    @Override
    public void showNewMessage(Message chatNew) {
        listMessages.add(chatNew);
        edtMessage.setText("");

        adapter.notifyDataSetChanged();
        adapter.notifyItemChanged(adapter.getItemCount() - 1);

        mRecyclerView.scrollToPosition(adapter.getItemCount() - 1);
    }
    //    onClick button
    public void OnClickBackChatMessage(View view){
        finish();
    }
}

