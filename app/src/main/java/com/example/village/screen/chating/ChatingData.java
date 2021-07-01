package com.example.village.screen.chating;

public class ChatingData {
    String uid;
    String content;

    public ChatingData(String uid, String content) {
        this.uid = uid;
        this.content = content;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
