package com.example.village.screen.chating;

import android.os.AsyncTask;

import com.example.village.databinding.ActivityChatingBinding;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ChatingDataAsyncTask extends AsyncTask {

    private FirebaseFirestore db;
    private String roomNumber;
    private ChatingViewModel viewModel;
    private int chatCount = 0;
    private ActivityChatingBinding binding;
    public ChatingDataAsyncTask(ActivityChatingBinding binding, ChatingViewModel viewModel, String roomNumber) {
        this.binding = binding;
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
                    try {
                        viewModel.setChatSum(Integer.parseInt(String.valueOf(documentSnapshot.get("chatSum"))));
                    } catch (NumberFormatException e) {
                        viewModel.setChatSum(0);
                    }

                    if (viewModel.getChatSum() > 0) {
                        for (int i = 1; i <= viewModel.getChatSum(); i++) {
                            String uid = String.valueOf(documentSnapshot.get("chat-uid-"+i));
                            String chat = String.valueOf(documentSnapshot.get("chat-"+i));
                            viewModel.addChatArrayList(uid, chat);
                            binding.chatRecyclerView.getAdapter().notifyDataSetChanged();
                            binding.chatRecyclerView.scrollToPosition(viewModel.getChatArrayList().size()-1);
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
