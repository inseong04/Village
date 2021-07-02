package com.example.village.screen.chat;

import android.os.AsyncTask;
import android.util.Log;

import com.example.village.databinding.FragmentChatBinding;
import com.example.village.util.GetTime;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ChatRoomDataAsyncTask extends AsyncTask {

    private FragmentChatBinding binding;
    private ChatViewModel viewModel;
    private int count;
    private FirebaseFirestore db;
    private String uid;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        db = FirebaseFirestore.getInstance();
        uid = FirebaseAuth.getInstance().getUid();
        count = 0;
    }



    public ChatRoomDataAsyncTask(FragmentChatBinding binding, ChatViewModel viewModel) {
        this.binding = binding;
        this.viewModel = viewModel;
    }

    @Override
    protected Object doInBackground(Object[] objects) {

        db.collection("users")
                .document(uid)
                .get()
                .addOnCompleteListener(task -> {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    ArrayList<String> roomList = (ArrayList<String>) documentSnapshot.get("roomList");
                    String myName = String.valueOf(documentSnapshot.get("name"));
                    if(count <= 0) {
                        try {
                        count = roomList.size();
                        viewModel.roomList = roomList;

                        } catch (NullPointerException e) {
                            return;
                        }
                    }

                    for (int i=0; i< count; i++) {
                        db.collection("chat").document(roomList.get(i))
                                .get()
                                .addOnCompleteListener(chatTask -> {
                                    DocumentSnapshot documentSnapshot1 = chatTask.getResult();
                                    ArrayList<String> nameList = (ArrayList<String>) documentSnapshot1.get("userNameList");
                                    ChatRoomData chatRoomData = new ChatRoomData("error", "error", "error");
                                    for (int j=0; j<2; j++) {
                                        if (!myName.equals(nameList.get(j))) {
                                            Log.e("test",roomList.get(0)+"::"+String.valueOf(documentSnapshot1.get("lastMessageTime")));
                                            chatRoomData = new ChatRoomData(
                                                    nameList.get(j),
                                                    String.valueOf(documentSnapshot1.get("lastMessage")),
                                                    GetTime.getTime(Long.parseLong(String.valueOf(documentSnapshot1.get("lastMessageTime"))))
                                            );
                                            break;
                                        }
                                    }
                                    viewModel.ChatListArrayList.add(chatRoomData);
                                    binding.recyclerView.getAdapter().notifyDataSetChanged();
                                });
                    }

                });

        return null;
    }

}
