package com.example.village.util;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;

public class GetTime{

    public static String getTime(long productTime) {
        long currentTime = System.currentTimeMillis();
        Log.e("getTime",String.valueOf((currentTime-productTime)/60000));
        if (currentTime- productTime < 60000) {
            return String.valueOf((currentTime- productTime) /1000)+"초 전";
        }
        else if (currentTime- productTime < 3600000) {
            return String.valueOf((currentTime-productTime) /60000)+"분 전";
        }
        else if (currentTime- productTime < 86400000) {
            return String.valueOf((currentTime- productTime) /3600000)+"시간 전";
        }
        else {
            return  String.valueOf((currentTime- productTime) /86400000)+"일 전";
        }
    }
}
