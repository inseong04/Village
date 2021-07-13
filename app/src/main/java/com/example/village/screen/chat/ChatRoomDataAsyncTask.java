package com.example.village.screen.chat;

import android.os.AsyncTask;
import com.example.village.databinding.FragmentChatBinding;
import com.example.village.util.GetTime;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

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
                        } catch (NullPointerException e) {
                            return;
                        }
                    }

                    for (int i=0; i< count; i++) {
                        int finalI = i;
                        db.collection("chat").document(roomList.get(i))
                                .get()
                                .addOnCompleteListener(chatTask -> {
                                    DocumentSnapshot documentSnapshot1 = chatTask.getResult();
                                    ArrayList<String> nameList = (ArrayList<String>) documentSnapshot1.get("userNameList");
                                    ChatRoomData chatRoomData = new ChatRoomData("error-1","error", "error", "error",6351, 1);
                                    for (int j=0; j<2; j++) {
                                        if (!myName.equals(nameList.get(j))) {
                                            int chatSum;
                                            try {
                                                chatSum = Integer.parseInt(String.valueOf(documentSnapshot1.get("chatSum"))) - Integer.parseInt(String.valueOf(documentSnapshot1.get(uid + "-chatCount")));
                                            } catch (NumberFormatException e) {
                                                chatSum = Integer.parseInt(String.valueOf(documentSnapshot1.get("chatSum")));
                                            }
                                            chatRoomData = new ChatRoomData(
                                                    roomList.get(finalI),
                                                    nameList.get(j),
                                                    String.valueOf(documentSnapshot1.get("lastMessage")),
                                                    GetTime.getTime(Long.parseLong(String.valueOf(documentSnapshot1.get("lastMessageTime")))),
                                                    Long.parseLong(String.valueOf(documentSnapshot1.get("lastMessageTime"))),
                                                    chatSum);
                                            break;
                                        }
                                    }
                                    viewModel.ChatListArrayList.add(chatRoomData);
                                    publishProgress(null);
                                });
                    }

                });

        return null;
    }
    @Override
    protected void onProgressUpdate(Object[] values) {
        super.onProgressUpdate(values);

        if (viewModel.ChatListArrayList.size() == 2) {
            if (viewModel.ChatListArrayList.get(0).lastMessageDate < viewModel.ChatListArrayList.get(1).lastMessageDate) {
                ArrayList<ChatRoomData> tempArray = new ArrayList<>();
                for (int i=1; i>=0; i--) {
                    ChatRoomData chatRoomData = new ChatRoomData(
                            viewModel.ChatListArrayList.get(i).roomNumber,
                            viewModel.ChatListArrayList.get(i).userName,
                            viewModel.ChatListArrayList.get(i).lastMessage,
                            viewModel.ChatListArrayList.get(i).date,
                            viewModel.ChatListArrayList.get(i).lastMessageDate,
                            viewModel.ChatListArrayList.get(i).unreadCount);
                    tempArray.add(chatRoomData);
                }
                viewModel.ChatListArrayList = tempArray;
            }
        }
        else if (viewModel.ChatListArrayList.size() > 2) {
            sortChatListArrayList();
        }

        binding.recyclerView.getAdapter().notifyDataSetChanged();
    }


    protected void sortChatListArrayList() {
        Long dateArr[] = new Long[viewModel.ChatListArrayList.size()];
        ArrayList<ChatRoomData> tempArray = new ArrayList<>();
        for (int i=0; i< viewModel.ChatListArrayList.size(); i++) {
            dateArr[i] = viewModel.ChatListArrayList.get(i).lastMessageDate;
        }
        Arrays.sort(dateArr, Collections.reverseOrder());
        for (long i : dateArr) {
        }
        for (int i=0; i< viewModel.ChatListArrayList.size(); i++) {
            for (int j=0; j < viewModel.ChatListArrayList.size(); j++) {
               if (dateArr[i] == viewModel.ChatListArrayList.get(j).lastMessageDate) {
                   ChatRoomData chatRoomData = new ChatRoomData(
                           viewModel.ChatListArrayList.get(j).roomNumber,
                           viewModel.ChatListArrayList.get(j).userName,
                           viewModel.ChatListArrayList.get(j).lastMessage,
                           viewModel.ChatListArrayList.get(j).date,
                           viewModel.ChatListArrayList.get(j).lastMessageDate,
                           viewModel.ChatListArrayList.get(j).unreadCount
                   );
                   tempArray.add(chatRoomData);
                   break;
               }
            }
        }
        viewModel.ChatListArrayList = tempArray;
    }
}
