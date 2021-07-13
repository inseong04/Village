package com.example.village.screen.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.village.R;
import com.example.village.databinding.FragmentChatBinding;

import java.util.ArrayList;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class Chat extends Fragment {

    private FragmentChatBinding binding;
    private Context mContext;
    private ChatViewModel viewModel;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false);
        viewModel = new ViewModelProvider((ViewModelStoreOwner) mContext).get(ChatViewModel.class);

        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            viewModel.ChatListArrayList = new ArrayList<>();
            ChatRoomDataAsyncTask chatRoomDataAsyncTask = new ChatRoomDataAsyncTask(binding, viewModel);
            chatRoomDataAsyncTask.execute();
            binding.swipeRefreshLayout.setRefreshing(false);
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.recyclerView.setLayoutManager(linearLayoutManager);
        ChatAdapter adapter = new ChatAdapter(this, mContext, viewModel);
        binding.recyclerView.setAdapter(adapter);

        if (viewModel.ChatListArrayList.size() <= 0) {
        ChatRoomDataAsyncTask chatRoomDataAsyncTask = new ChatRoomDataAsyncTask(binding, viewModel);
        chatRoomDataAsyncTask.execute();
        }

        return binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101 && resultCode == RESULT_CANCELED) {
                viewModel.ChatListArrayList = new ArrayList<>();
                ChatRoomDataAsyncTask chatRoomDataAsyncTask = new ChatRoomDataAsyncTask(binding, viewModel);
                chatRoomDataAsyncTask.execute();
        }
    }
}