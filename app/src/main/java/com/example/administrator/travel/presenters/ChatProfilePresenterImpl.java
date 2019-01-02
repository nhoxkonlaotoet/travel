package com.example.administrator.travel.presenters;

import android.content.SharedPreferences;

import com.example.administrator.travel.models.ChatProfileInteractor;
import com.example.administrator.travel.models.ChatProfileInteractorImpl;
import com.example.administrator.travel.models.ChatProfileListener;
import com.example.administrator.travel.views.ChatProfileView;

/**
 * Created by Henry
 */

public class ChatProfilePresenterImpl implements ChatProfilePresenter,ChatProfileListener{

    ChatProfileView chatProfileView;
    ChatProfileInteractor chatProfileInteractor;

    public ChatProfilePresenterImpl(ChatProfileView view){
        this.chatProfileView = view;
        chatProfileInteractor = new ChatProfileInteractorImpl();
    }

    @Override
    public void getUserProfilePresenter(SharedPreferences sharedPreferences) {
        chatProfileInteractor.getUserProfileInteractor(this,sharedPreferences);
    }

    @Override
    public void onResultGetUserProfileListener(String mName, String mSDT, String mSex, String mEmail, String mURL) {
        chatProfileView.showResultGetProfileUser(mName,mSDT,mSex,mEmail,mURL);
    }
}
