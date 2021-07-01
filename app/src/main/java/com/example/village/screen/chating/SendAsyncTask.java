package com.example.village.screen.chating;

import android.os.AsyncTask;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SendAsyncTask extends AsyncTask {

    Map<String, Object> map;
    String uid;
    String roomNumber;
    SendAsyncTask(String uid, String roomNumber, Map<String, Object> map ) {
        this.uid = uid;
        this.roomNumber = roomNumber;
        this.map = map;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users").document(uid)
                .get()
                .addOnCompleteListener(task -> {
                    ArrayList<String> arrayList;
                    try {
                        arrayList = (ArrayList<String>) task.getResult().get("roomList");
                    } catch (Exception e) {
                        arrayList = new ArrayList<>();
                    }
                    arrayList.add(roomNumber);
                    Map<String, Object> map2 = new HashMap<>();
                    map2.put("roomList",arrayList);
                    db.collection("users").document(uid)
                            .update(map2);
                });

        db.collection("chat").document(roomNumber)
                .set(map, SetOptions.merge());
        return null;
    }
}
