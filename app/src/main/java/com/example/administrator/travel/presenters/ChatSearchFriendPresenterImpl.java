package com.example.administrator.travel.presenters;

import android.content.SharedPreferences;
import android.view.View;

import com.example.administrator.travel.models.ChatSearchFriendInteractor;
import com.example.administrator.travel.models.ChatSearchFriendInteractorImpl;
import com.example.administrator.travel.models.ChatSearchFriendListener;
import com.example.administrator.travel.models.entities.UserInformation;
import com.example.administrator.travel.views.ChatSearchFriendView;

import java.util.List;

/**
 * Created by Henry on 12/14/2018.
 */

public class ChatSearchFriendPresenterImpl implements ChatSearchFriendPresenter,ChatSearchFriendListener {

    ChatSearchFriendInteractor chatSearchFriendInteractor;
    ChatSearchFriendView chatSearchFriendView;

    public ChatSearchFriendPresenterImpl(ChatSearchFriendView chatSearchFriendView){
        this.chatSearchFriendView = chatSearchFriendView;
        chatSearchFriendInteractor = new ChatSearchFriendInteractorImpl();
    }

    @Override
    public void getSearchFriendPresenter(String searchName) {
        chatSearchFriendInteractor.setSearchName(searchName);
        chatSearchFriendInteractor.getListFriendSearch(this);
    }

    @Override
    public void getFriendInvitationPresenter(SharedPreferences sharedPreferences) {
        chatSearchFriendInteractor.getFriendInvitation(this,sharedPreferences);
    }

    @Override
    public void getListUserSearchListener(List<UserInformation> listUserSearch, List<String> UID) {
        chatSearchFriendView.ShowListUserSearch(listUserSearch,UID);
    }

    @Override
    public void getFriendInvitationListener(List<UserInformation> listUserSearch, List<String> UID) {
        chatSearchFriendView.ShowFriendInvitation(listUserSearch,UID);
    }
}
