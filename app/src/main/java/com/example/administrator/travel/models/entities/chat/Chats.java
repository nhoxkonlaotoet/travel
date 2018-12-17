package com.example.administrator.travel.models.entities.chat;

/**
 * Created by Henry on 12/13/2018.
 */

public class Chats {
    String lastMessage;
    long timestamp;

    public Chats(String lastMessage, long timestamp) {
        this.lastMessage = lastMessage;
        this.timestamp = timestamp;
    }

    public Chats() {
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
