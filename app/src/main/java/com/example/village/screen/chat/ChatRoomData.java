package com.example.village.screen.chat;

public class ChatRoomData {
    String roomNumber;
    String userName;
    String lastMessage;
    String date;
    long lastMessageDate;
    int unreadCount;

    public ChatRoomData(String roomNumber, String userName, String lastMessage, String date, long lastMessageDate, int unreadCount) {
        this.roomNumber = roomNumber;
        this.userName = userName;
        this.lastMessage = lastMessage;
        this.date = date;
        this.lastMessageDate = lastMessageDate;
        this.unreadCount = unreadCount;
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

    public void setDate(String date) {
        this.date = date;
    }

    public void setLastMessageDate(long lastMessageDate) {
        this.lastMessageDate = lastMessageDate;
    }

}
