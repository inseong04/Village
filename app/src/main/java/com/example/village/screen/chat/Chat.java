package com.example.village.screen.chat;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.village.R;
import com.example.village.databinding.FragmentChatBinding;

public class Chat extends Fragment {

    private FragmentChatBinding binding;
    private Context mContext;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false);
        ChatViewModel viewModel = new ViewModelProvider((ViewModelStoreOwner) mContext).get(ChatViewModel.class);

        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            viewModel.ChatListArrayList.clear();
            ChatRoomDataAsyncTask chatRoomDataAsyncTask = new ChatRoomDataAsyncTask(binding, viewModel);
            chatRoomDataAsyncTask.execute();
            binding.swipeRefreshLayout.setRefreshing(false);
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.recyclerView.setLayoutManager(linearLayoutManager);
        ChatAdapter adapter = new ChatAdapter(mContext, viewModel);
        binding.recyclerView.setAdapter(adapter);

        if (viewModel.ChatListArrayList.size() <= 0) {
        ChatRoomDataAsyncTask chatRoomDataAsyncTask = new ChatRoomDataAsyncTask(binding, viewModel);
        chatRoomDataAsyncTask.execute();
        }

        return binding.getRoot();
    }
}