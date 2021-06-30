package com.example.village.screen.chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.village.R;
import com.example.village.databinding.ActivityChatingBinding;
import com.example.village.screen.home.HomeViewModel;
import com.example.village.screen.post.PostRentalDialogFragment;
import com.example.village.util.WarningDialogFragment;

public class Chating extends AppCompatActivity {

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

        LinearLayoutManager

        ChatingPostAsyncTask chatingPostAsyncTask = new ChatingPostAsyncTask(binding, viewModel, postNumber);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            chatingPostAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            chatingPostAsyncTask.execute();
        }
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