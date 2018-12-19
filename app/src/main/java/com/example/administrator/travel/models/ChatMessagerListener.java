package com.example.administrator.travel.models;

import com.example.administrator.travel.models.entities.chat.Message;

/**
 * Created by Henry on 12/18/2018.
 */

public interface ChatMessagerListener {
    public void getMessageListener(Message chatNew);
}
