package com.example.village.screen.chat;

import android.os.AsyncTask;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ChatingDataAsyncTask extends AsyncTask {

    private FirebaseFirestore db;
    private String roomNumber;
    private ChatingViewModel viewModel;
    private int chatCount = 0;
    public ChatingDataAsyncTask(ChatingViewModel viewModel, String roomNumber) {
        this.viewModel = viewModel;
        this.roomNumber = roomNumber;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        db = FirebaseFirestore.getInstance();
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        db.collection("chat").document(roomNumber)
                .get()
                .addOnCompleteListener(task -> {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    int chatSum = Integer.parseInt(String.valueOf(documentSnapshot.get("chatSum")));

                    if (chatSum > 0) {
                        for (int i = 1; i <= chatSum; i++) {
                            int type = Integer.parseInt(String.valueOf(documentSnapshot.get("chatType-" + i)));
                            String chat = String.valueOf(documentSnapshot.get("chat-"+i));
                            viewModel.addChatArrayList(type, chat);
                        }
                    } else {
                        // TODO 여기에 chatSum이  0 일때 처리해주어야함.
                    }

                });

        return null;
    }

    @Override
    protected void onProgressUpdate(Object[] values) {
        super.onProgressUpdate(values);
    }
}
