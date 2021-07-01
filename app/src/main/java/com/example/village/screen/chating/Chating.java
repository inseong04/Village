package com.example.village.screen.chating;

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

import java.util.HashMap;
import java.util.Map;

public class Chating extends AppCompatActivity {

    // require : postNumber , roomNumber ( String room = postNumber+"-"+roomNumber; )

    private ActivityChatingBinding binding;
    private ChatingViewModel viewModel;
    private String postNumber;
    private String roomNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chating);
        viewModel = new ViewModelProvider(this).get(ChatingViewModel.class);
        Intent intent = getIntent();
        postNumber = intent.getStringExtra("postNumber");
        roomNumber = intent.getStringExtra("roomNumber");
        viewModel.setPostNumber(postNumber);
        viewModel.setRoomNumber(roomNumber);

        binding.setActivity(this);
        binding.setViewModel(viewModel);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.chatRecyclerView.setLayoutManager(linearLayoutManager);
        binding.chatRecyclerView.setAdapter(new ChatingAdapter(viewModel));

        ChatingPostAsyncTask chatingPostAsyncTask = new ChatingPostAsyncTask(this, binding, postNumber);
        ChatingDataAsyncTask chatingDataAsyncTask = new ChatingDataAsyncTask(binding, viewModel, roomNumber);
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
        String uid = FirebaseAuth.getInstance().getUid();
        String text = binding.chatEtv.getText().toString();
        viewModel.addChatArrayList(viewModel.getUid(), text);
        viewModel.setChatSum(viewModel.getChatSum()+1);
        Map<String, Object> map = new HashMap<>();
        map.put("uid", text);
        map.put("chat-"+viewModel.getChatSum(), text);
        map.put("chat-uid-"+viewModel.getChatSum(), uid);
        map.put("chatSum",viewModel.getChatSum());
        SendAsyncTask sendAsyncTask = new SendAsyncTask(uid, roomNumber, map);
        sendAsyncTask.execute();
        binding.chatEtv.setText("");
        Log.e("test","touch3");
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
}