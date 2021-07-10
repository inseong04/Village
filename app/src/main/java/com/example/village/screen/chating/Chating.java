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
import android.view.Gravity;
import android.view.View;

import com.example.village.R;
import com.example.village.databinding.ActivityChatingBinding;
import com.example.village.screen.post.Post;
import com.example.village.util.Dialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

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
    private String receiverName;
    private String[] rentalProduct = new String[1];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chating);
        viewModel = new ViewModelProvider(this).get(ChatingViewModel.class);
        Intent intent = getIntent();
        receiverName = intent.getStringExtra("receiverName");
        postNumber = intent.getStringExtra("postNumber");
        roomNumber = intent.getStringExtra("roomNumber");
        sellerUid = intent.getStringExtra("sellerUid");
        viewModel.setPostNumber(postNumber);
        viewModel.setRoomNumber(roomNumber);
        uid = FirebaseAuth.getInstance().getUid();

        binding.setActivity(this);
        binding.setViewModel(viewModel);

        FirebaseFirestore.getInstance().collection("post").document(postNumber)
                .get()
                .addOnCompleteListener(task -> {
                   viewModel.setRental((Boolean) task.getResult().get("rental"));
                });

        if (uid.equals(sellerUid)) {
            binding.rentalBtn.setVisibility(View.GONE);
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
            Thread thread = new Thread(
                    () -> {
                        FirebaseFirestore.getInstance().collection("users")
                                .document(uid)
                                .get()
                                .addOnCompleteListener(task -> {
                                    try {

                                        Log.e("test","sx");
                                        rentalProduct = ((String) task.getResult().get("rentalProduct")).split("-");
                                        for (int i = 0; i < rentalProduct.length; i++) {
                                            Log.e("test", postNumber + "----" + rentalProduct[i]);
                                            if (rentalProduct[i].equals(postNumber)) {
                                                binding.rentalEndBtn.setVisibility(View.VISIBLE);
                                                binding.rentalBtn.setVisibility(View.GONE);
                                            }
                                        }
                                    } catch (NullPointerException e) {
                                        Log.e("test","test2");
                                        rentalProduct = new String[0];
                                    }
                                });
                    });
            thread.start();

        }





            FirebaseFirestore.getInstance().collection("chat")
                    .document(roomNumber)
                    .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                            try {
                                Thread thread = new Thread(
                                        () -> {
                                            Map<String, Object> map = new HashMap<>();
                                            map.put(uid+"-chatCount", value.get("chatSum"));
                                            FirebaseFirestore.getInstance().collection("chat")
                                                    .document(roomNumber)
                                                    .update(map);
                                        });
                                thread.start();
                            if (value != null && value.exists()) {
                                if (!value.getData().get(uid).equals(value.getData().get("lastMessage").toString())
                                && viewModel.getChatArrayList().size() > 0 && !viewModel.getChatArrayList().get(viewModel.getChatArrayList().size()-1).content.equals(value.getData().get("lastMessage").toString())) {
                                    Log.e("test","vd");
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

        ChatingPostAsyncTask chatingPostAsyncTask = new ChatingPostAsyncTask(this, binding, postNumber, receiverName);
        ChatingDataAsyncTask chatingDataAsyncTask = new ChatingDataAsyncTask(sellerUid, binding, viewModel, roomNumber);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            chatingPostAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            chatingDataAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            chatingPostAsyncTask.execute();
            chatingDataAsyncTask.execute();
        }

        binding.postWholeLayout.setOnClickListener(v -> {
            Intent intent1 = new Intent(getApplicationContext(), Post.class);
            intent1.putExtra("postNumber", Integer.parseInt(postNumber));
            intent1.putExtra("chatIntent", true);
            startActivity(intent1);
        });

    }

    public void sendMessage(View view) {

        if (!binding.chatEtv.getText().toString().equals("")) {

            long lastMessageTime = System.currentTimeMillis();
            String text = binding.chatEtv.getText().toString();
            viewModel.addChatArrayList(viewModel.getUid(), text);
            viewModel.setChatSum(viewModel.getChatSum() + 1);
            Map<String, Object> map = new HashMap<>();
            map.put("lastMessageTime", lastMessageTime);
            map.put(uid, text);
            map.put("lastMessage", text);
            map.put("chat-" + viewModel.getChatSum(), text);
            map.put("chat-uid-" + viewModel.getChatSum(), uid);
            map.put("chatSum", viewModel.getChatSum());
            SendAsyncTask sendAsyncTask = new SendAsyncTask(sellerUid, uid, roomNumber, map);
            sendAsyncTask.execute();
            binding.chatEtv.setText("");
            binding.chatRecyclerView.getAdapter().notifyDataSetChanged();
            binding.chatRecyclerView.smoothScrollToPosition(viewModel.getChatArrayList().size() - 1);
            Thread thread = new Thread(
                    () -> {
                        Map<String, Object> map2 = new HashMap<>();
                        map.put(uid+"-chatCount", viewModel.getChatSum());
                        FirebaseFirestore.getInstance().collection("chat")
                                .document(roomNumber)
                                .update(map2);
                    });
            thread.start();
        }

    }

    public void rentalEnd(View view) {
        ChatRentalEndDialog chatRentalEndDialog = new ChatRentalEndDialog(Chating.this, getSupportFragmentManager(), getResources().getDisplayMetrics()
        , postNumber, rentalProduct);
        chatRentalEndDialog.getWindow().setGravity(Gravity.CENTER);
        chatRentalEndDialog.show();
    }

    public void callDialog(View view) {

        if (viewModel.getRental()) {
            Dialog dialog = new Dialog(getApplicationContext(),getResources().getDisplayMetrics(), "대여하기", "이미 대여중인 상품입니다.");
            dialog.getWindow().setGravity(Gravity.CENTER);
            dialog.show();
        } else {

            if (viewModel.getWarningRun()) {
                PostRentalDialogFragment postRentalDialogFragment = new PostRentalDialogFragment(getApplicationContext(), Chating.this, binding.getTitle(), postNumber);
                postRentalDialogFragment.show(getSupportFragmentManager(), "postRentalDialog");
            } else {
                viewModel.setWarningRun(true);
                ChatWarningDialog chatWarningDialog = new ChatWarningDialog(Chating.this, getSupportFragmentManager(), this,
                        binding.getTitle(), postNumber, getResources().getDisplayMetrics());
                chatWarningDialog.getWindow().setGravity(Gravity.CENTER);
                chatWarningDialog.show();

            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_OK);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


}