package com.example.village.screen.chating;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.village.R;
import com.example.village.databinding.ActivityChatingBinding;
import com.example.village.screen.post.PostRentalDialogFragment;
import com.example.village.util.WarningDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Chating extends AppCompatActivity {

    // require : postNumber , roomNumber ( String room = postNumber+"-"+roomNumber; ), sellerUid

    private ActivityChatingBinding binding;
    private ChatingViewModel viewModel;
    private String postNumber;
    private String roomNumber;
    private String uid;
    private String sellerUid;
    private String receiveUid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chating);
        viewModel = new ViewModelProvider(this).get(ChatingViewModel.class);
        Intent intent = getIntent();
        postNumber = intent.getStringExtra("postNumber");
        roomNumber = intent.getStringExtra("roomNumber");
        sellerUid = intent.getStringExtra("sellerUid");
        viewModel.setPostNumber(postNumber);
        viewModel.setRoomNumber(roomNumber);
        uid = FirebaseAuth.getInstance().getUid();

        binding.setActivity(this);
        binding.setViewModel(viewModel);

        if (uid.equals(sellerUid)) {
            FirebaseFirestore.getInstance().collection("chat").document(roomNumber)
                    .get()
                    .addOnCompleteListener(task -> {
                        ArrayList<String> arrayList = (ArrayList<String>) task.getResult().get("uidList");
                        if (uid.equals(arrayList.get(0))) {
                            receiveUid = arrayList.get(1);
                        }
                    });
        }
        else {
            receiveUid = sellerUid;
        }


            FirebaseFirestore.getInstance().collection("chat")
                    .document(roomNumber)
                    .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                            try {
                            if (value != null && value.exists()) {
                                if (!value.getData().get(uid).equals(value.getData().get("lastMessage").toString())) {
                                    viewModel.addChatArrayList(receiveUid, value.getData().get("lastMessage").toString());
                                    binding.chatRecyclerView.getAdapter().notifyDataSetChanged();
                                    binding.chatRecyclerView.smoothScrollToPosition(viewModel.getChatArrayList().size() - 1);
                                }
                            }
                            } catch (Exception e) {
                            }

                        }
                    });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.chatRecyclerView.setLayoutManager(linearLayoutManager);
        binding.chatRecyclerView.setAdapter(new ChatingAdapter(viewModel));

        ChatingPostAsyncTask chatingPostAsyncTask = new ChatingPostAsyncTask(this, binding, postNumber);
        ChatingDataAsyncTask chatingDataAsyncTask = new ChatingDataAsyncTask(sellerUid, binding, viewModel, roomNumber);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            chatingPostAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            chatingDataAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            chatingPostAsyncTask.execute();
            chatingDataAsyncTask.execute();
        }

        binding.sendLayout.setOnClickListener(v -> {
            sendMessage();
        });

        binding.sendBtn.setOnClickListener(v -> {
            sendMessage();
        });

    }

    private void sendMessage() {

        long lastMessageTime = System.currentTimeMillis();
        String text = binding.chatEtv.getText().toString();
        viewModel.addChatArrayList(viewModel.getUid(), text);
        viewModel.setChatSum(viewModel.getChatSum()+1);
        Map<String, Object> map = new HashMap<>();
        map.put("lastMessageTime", lastMessageTime);
        map.put(uid, text);
        map.put("lastMessage", text);
        map.put("chat-"+viewModel.getChatSum(), text);
        map.put("chat-uid-"+viewModel.getChatSum(), uid);
        map.put("chatSum",viewModel.getChatSum());
        SendAsyncTask sendAsyncTask = new SendAsyncTask(sellerUid, uid, roomNumber, map);
        sendAsyncTask.execute();
        binding.chatEtv.setText("");
        binding.chatRecyclerView.getAdapter().notifyDataSetChanged();
        binding.chatRecyclerView.smoothScrollToPosition(viewModel.getChatArrayList().size()-1);
    }

    public void callDialog(View view) {
        if (viewModel.getRental()) {
            WarningDialogFragment warningDialogFragment = new WarningDialogFragment("대여하기", "이미 대여중인 상품입니다.");
            warningDialogFragment.show(getSupportFragmentManager(), "dialogFragment");
        } else {
            PostRentalDialogFragment postRentalDialogFragment = new PostRentalDialogFragment(this, binding.getTitle(), postNumber);
            postRentalDialogFragment.show(getSupportFragmentManager(), "postRentalDialog");
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_OK);
        Log.e("vcdszgd","jtedhrsgedafsgrthykjrewr");
        finish();
    }
}