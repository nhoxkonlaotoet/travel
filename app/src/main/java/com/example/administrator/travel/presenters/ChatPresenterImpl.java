package com.example.administrator.travel.presenters;

import android.content.SharedPreferences;

import com.example.administrator.travel.models.ChatInteractor;
import com.example.administrator.travel.models.ChatInteractorImpl;
import com.example.administrator.travel.models.ChatListener;
import com.example.administrator.travel.models.entities.chat.Chats;
import com.example.administrator.travel.views.ChatView;

import java.util.List;

/**
 * Created by Henry on 12/19/2018.
 */

public class ChatPresenterImpl implements ChatPresenter,ChatListener {
    ChatView chatView;
    ChatInteractor chatInteractor;

    public ChatPresenterImpl(ChatView view){
        this.chatView = view;
        this.chatInteractor = new ChatInteractorImpl();
    }
    @Override
    public void getChatPresenter(SharedPreferences sharedPreferences) {
        chatInteractor.getChatInteractor(this,sharedPreferences);
    }

    @Override
    public void onResultGetChatListener(List<Chats> lstLastChat, List<String> lstKeyFriend, List<String> lstUserGroup) {
        chatView.onResultGetChatView(lstLastChat,lstKeyFriend,lstUserGroup);
    }
}
