package com.example.village.screen.my;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UpdateLocation extends Thread{

    private String location;

    UpdateLocation(String location) {
        this.location = location;
    }

    private String[] writtenPostList;

    public void run () {
        super.run();

        FirebaseFirestore.getInstance().collection("users").document(FirebaseAuth.getInstance().getUid())
                .get()
                .addOnCompleteListener(task -> {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    try {
                        writtenPostList = ((String) documentSnapshot.get("writtenPost")).split("-");
                        postUpdate();

                    } catch (Exception e) {

                    }

                });
    }

    private int postCount = 0;
    private void postUpdate () {

        if (postCount < writtenPostList.length) {
            Map<String, Object> map = new HashMap<>();
            map.put("location", location);
            FirebaseFirestore.getInstance().collection("post")
                    .document(writtenPostList[postCount])
                    .update(map);
            ++postCount;
            postUpdate();
        }

    }
}
