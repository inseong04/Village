package com.example.village.screen.chat;

public class ChatRoomData {
    String userName;
    String lastMessage;
    String date;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ChatRoomData(String userName, String lastMessage, String date) {
        this.userName = userName;
        this.lastMessage = lastMessage;
        this.date = date;
    }
}
