package com.example.administrator.travel.presenters;

import android.content.SharedPreferences;

import com.example.administrator.travel.models.ChatContactFriendInteractor;
import com.example.administrator.travel.models.ChatContactFriendInteractorImpl;
import com.example.administrator.travel.models.ChatContactFriendListener;
import com.example.administrator.travel.views.fragments.ChatContactFriendView;

import java.util.List;

/**
 * Created by Henry on 12/17/2018.
 */

public class ChatContactFriendPresenterImpl implements ChatContactFriendPresenter,ChatContactFriendListener {

    ChatContactFriendView chatContactFriendView;
    ChatContactFriendInteractor chatContactFriendInteractor;

    public ChatContactFriendPresenterImpl(ChatContactFriendView chatContactFriendView){
        this.chatContactFriendView = chatContactFriendView;
        chatContactFriendInteractor = new ChatContactFriendInteractorImpl();
    }

    @Override
    public void getFriendsPresenter(SharedPreferences sharedPreferences) {
        chatContactFriendInteractor.getFriendsInteractor(this,sharedPreferences);
    }

    @Override
    public void onResultGetFriendsListener(List<String> listKeyFrs, List<String> listUserGr) {
        chatContactFriendView.onResultGetFriends(listKeyFrs,listUserGr);
    }
}
