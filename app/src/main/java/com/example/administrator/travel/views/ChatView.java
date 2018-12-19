package com.example.administrator.travel.views;

import com.example.administrator.travel.models.entities.chat.Chats;

import java.util.List;

/**
 * Created by Henry on 12/19/2018.
 */

public interface ChatView {
    public void onResultGetChatView(List<Chats> lstLastChat, List<String> lstKeyFriend, List<String> lstUserGroup);
}
