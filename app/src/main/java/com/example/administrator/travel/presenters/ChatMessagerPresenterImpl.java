package com.example.administrator.travel.presenters;

import android.content.SharedPreferences;

import com.example.administrator.travel.models.ChatMessagerInteractor;
import com.example.administrator.travel.models.ChatMessagerInteractorImpl;
import com.example.administrator.travel.models.ChatMessagerListener;
import com.example.administrator.travel.models.entities.chat.Message;
import com.example.administrator.travel.views.ChatMessagerView;

/**
 * Created by Henry on 12/18/2018.
 */

public class ChatMessagerPresenterImpl implements ChatMessagerPresenter, ChatMessagerListener {

    ChatMessagerView chatMessagerView;
    ChatMessagerInteractor chatMessagerInteractor;

    public ChatMessagerPresenterImpl(ChatMessagerView view){
        this.chatMessagerView = view;
        chatMessagerInteractor = new ChatMessagerInteractorImpl();
    }
    @Override
    public void getMessagePresenter(SharedPreferences sharedPreferences) {
        chatMessagerInteractor.getMessageIntractor(this,sharedPreferences);
    }

    @Override
    public void SendMessagePresenter(SharedPreferences sharedPreferences, String message) {
        chatMessagerInteractor.SendMessageInteractor(sharedPreferences,message);
    }

    @Override
    public void getMessageListener(Message chatNew) {
        chatMessagerView.showNewMessage(chatNew);
    }
}
