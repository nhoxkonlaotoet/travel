package com.example.administrator.travel.views.fragments;

import java.util.List;

/**
 * Created by Henry on 12/17/2018.
 */

public interface FriendContactView {
    void showFriends(List<String> friendIdList);

    void gotoChatAcitivty(String friendId);

    void notify(String message);
}
