package com.example.administrator.travel.models;

import com.example.administrator.travel.models.entities.chat.Chats;

import java.util.List;

/**
 * Created by Henry on 12/19/2018.
 */

public interface ChatListener {
    public void onResultGetChatListener(List<Chats> lstLastChat,List<String> lstKeyFriend,List<String> lstUserGroup);
}
