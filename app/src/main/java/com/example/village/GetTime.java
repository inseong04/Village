package com.example.village;

import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GetTime{

    private SimpleDateFormat dayTime;
    private FirebaseFirestore db;
    private long postTime;

    public GetTime(String postTimeStr) {
        this.postTime = Long.parseLong(postTimeStr);
        this.dayTime = new SimpleDateFormat("yyyy-MM-dd a hh:mm:ss");
        this.db = FirebaseFirestore.getInstance();
    }

    public String getTime() {
        long currentTime = System.currentTimeMillis();

        if (currentTime-postTime < 60000) {
            return String.valueOf(currentTime-postTime/1000)+"초 전";
        }
        else if (currentTime-postTime < 3600000) {
            return String.valueOf(currentTime-postTime/60000)+"분 전";
        }
        else if (currentTime-postTime < 86400000) {
            return String.valueOf(currentTime-postTime/3600000)+"시간 전";
        }
        else {
            return  String.valueOf(currentTime-postTime/86400000)+"일 전";
        }
    }
}
