package com.example.administrator.travel.models.bases;

import com.example.administrator.travel.models.listeners.Listener;

public interface FriendInteractor {
    void sendAddFriendRequest(String myId, String friendId, Listener.OnSendAddFriendRequestFinishedListener listener);

    void acceptFriendRequest(String addFriendRequestId, String myId);

    void removeFriend(String addFriendRequestId, String myId);

    void getFriends(String userId, Listener.OnGetFriendsFinishedListener listener);
}
