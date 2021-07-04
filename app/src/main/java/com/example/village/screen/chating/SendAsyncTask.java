package com.example.village.screen.chating;

import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class SendAsyncTask extends AsyncTask {

    Map<String, Object> map;
    String uid;
    String roomNumber;
    String sellerUid;
    SendAsyncTask(String sellerUid, String uid, String roomNumber, Map<String, Object> map ) {
        this.sellerUid = sellerUid;
        this.uid = uid;
        this.roomNumber = roomNumber;
        this.map = map;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        AtomicReference<Boolean> roomExistence = new AtomicReference<>(false);
        db.collection("users").document(uid)
                .get()
                .addOnCompleteListener(task -> {
                    ArrayList<String> arrayList;
                    try {
                        arrayList = (ArrayList<String>) task.getResult().get("roomList");
                    } catch (Exception e) {
                        arrayList = new ArrayList<>();
                    }

                        try {
                            for (int i = 0; i < arrayList.size() ; i++) {
                                if (roomNumber.equals(arrayList.get(i))) {
                                    roomExistence.set(true);
                                    break;
                                }
                            }
                        } catch (Exception e) {

                        }

                        if (!roomExistence.get()) {
                            try {
                                arrayList.add(roomNumber);
                            } catch (NullPointerException e) {
                                arrayList = new ArrayList<>();
                                arrayList.add(roomNumber);
                            }
                            Map<String, Object> map2 = new HashMap<>();
                            map2.put("roomList", arrayList);
                            db.collection("users").document(uid)
                                    .update(map2);
                            Thread thread1 = new Thread(
                                    () -> {
                                        db.collection("users").document(sellerUid)
                                        .get()
                                        .addOnCompleteListener(sellerUidTask -> {
                                           DocumentSnapshot documentSnapshot = sellerUidTask.getResult();
                                           Map<String, Object> map3 = new HashMap<>();

                                           if (documentSnapshot.get("roomList") == null) {
                                               map3.put("roomList", roomNumber);
                                           }
                                           else {
                                               map3.put("roomList", documentSnapshot.get("roomList"));
                                           }

                                           db.collection("users").document(sellerUid)
                                                   .update(map3);
                                        });
                                    }
                            );
                            thread1.start();
                            Thread thread2 = new Thread(
                                    () -> {
                                        db.collection("users").document(sellerUid)
                                                .get()
                                                .addOnCompleteListener(sellerTask -> {
                                                    DocumentSnapshot documentSnapshot = sellerTask.getResult();
                                                    ArrayList<String> sellerRoomList = (ArrayList<String>) documentSnapshot.get("roomList");
                                                    sellerRoomList.add(roomNumber);
                                                    Map<String, Object> map3 = new HashMap<>();
                                                    map3.put("roomList", sellerRoomList);
                                                    db.collection("users").document(sellerUid)
                                                            .update(map3);
                                                });
                                    }
                            );
                            thread2.start();
                        }
                });

        db.collection("chat").document(roomNumber)
                .set(map, SetOptions.merge());
        return null;
    }
}
