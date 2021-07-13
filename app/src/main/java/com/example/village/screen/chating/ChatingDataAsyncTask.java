package com.example.village.screen.chating;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.example.village.databinding.ActivityChatingBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChatingDataAsyncTask extends AsyncTask {

    private FirebaseFirestore db;
    private String roomNumber;
    private ChatingViewModel viewModel;
    private ActivityChatingBinding binding;
    private String sellerUid;
    public ChatingDataAsyncTask(String sellerUid, ActivityChatingBinding binding, ChatingViewModel viewModel, String roomNumber) {
        this.sellerUid = sellerUid;
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
                        Thread thread = new Thread(
                                () -> {

                                    ArrayList<String> uidList = new ArrayList<>();
                                    uidList.add(sellerUid);
                                    uidList.add(FirebaseAuth.getInstance().getUid());


                                    ArrayList<String> userNameArray = new ArrayList<>();
                                    for (int i = 0; i <2; i++) {

                                        int finalI = i;
                                        db.collection("users").document(uidList.get(i))
                                                .get()
                                                .addOnCompleteListener(usersTask -> {
                                                    DocumentSnapshot documentSnapshot1 = usersTask.getResult();
                                                    userNameArray.add(String.valueOf(documentSnapshot1.get("name")));
                                                    if (userNameArray.size() == 2) {
                                                        Map<String, Object> map = new HashMap<>();
                                                        map.put("userNameList", userNameArray);
                                                        map.put("uidList", uidList);
                                                        map.put("sellerUid", sellerUid);
                                                        map.put("consumerUid", FirebaseAuth.getInstance().getUid());
                                                        db.collection("chat").document(roomNumber)
                                                                .set(map, SetOptions.merge());
                                                    }
                                                });

                                    }
                                });
                        thread.start();
                    }

                });

        return null;
    }

    @Override
    protected void onProgressUpdate(Object[] values) {
        super.onProgressUpdate(values);
    }
}
