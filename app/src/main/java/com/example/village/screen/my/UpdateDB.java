package com.example.village.screen.my;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UpdateDB extends Thread {

    private String username;
    private String newName;
    UpdateDB(String username, String newName) {
        this.username = username;
        this.newName = newName;
    }

    private int count = 0;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ArrayList<String> roomList;
    private String[] writtenPostList;

    public void run() {
        super.run();

        db.collection("users").document(FirebaseAuth.getInstance().getUid())
                .get()
                .addOnCompleteListener(task -> {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    roomList = (ArrayList<String>) documentSnapshot.get("roomList");
                    if (!roomList.isEmpty()) {
                        roomUpdate();
                    }

                    try {
                        writtenPostList = ((String) documentSnapshot.get("writtenPost")).split("-");
                        postUpdate();

                    } catch (Exception e) {

                    }

                });

    }
    private int roomCount = 0;
    private void roomUpdate() {

        if (roomCount <roomList.size()) {
            db.collection("chat")
                    .document(roomList.get(roomCount))
                    .get()
                    .addOnCompleteListener(task1 -> {
                        ArrayList<String> nameList = (ArrayList<String>) task1.getResult().get("userNameList");
                        if (!nameList.isEmpty()) {
                            if (nameList.get(0).equals(username)) {
                                nameList.remove(0);
                                nameList.add(newName);
                            } else {
                                nameList.remove(1);
                                nameList.add(newName);
                            }

                            Map<String, Object> map = new HashMap<>();
                            map.put("nameList", nameList);
                            map.put("userNameList", nameList);
                            db.collection("chat")
                                    .document(roomList.get(roomCount))
                                    .update(map);
                            ++roomCount;
                            roomUpdate();
                        }

                    });
        }
    }

    private int postCount = 0;
    private void postUpdate() {

        if (postCount < writtenPostList.length) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", newName);
            db.collection("post")
                    .document(writtenPostList[postCount])
                    .update(map);
            ++postCount;
            postUpdate();
        }
    }
}

