package com.example.village.screen.chat;

public class ChatRoomData {
    String roomNumber;
    String userName;
    String lastMessage;
    String date;
    long lastMessageDate;

    public ChatRoomData(String roomNumber, String userName, String lastMessage, String date, long lastMessageDate) {
        this.roomNumber = roomNumber;
        this.userName = userName;
        this.lastMessage = lastMessage;
        this.date = date;
        this.lastMessageDate = lastMessageDate;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

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

    public long getLastMessageDate() {
        return lastMessageDate;
    }

    public void setLastMessageDate(long lastMessageDate) {
        this.lastMessageDate = lastMessageDate;
    }
}
