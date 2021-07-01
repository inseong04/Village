package com.example.village.screen.home;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

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
import com.example.village.databinding.FragmentHomeBinding;
import com.example.village.screen.home.search.SearchActivity;
import com.example.village.screen.productwriting.ProductWriting;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Home extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewModel viewModel;
    protected FirebaseFirestore db;
    private Context mContext;
    HomeAdapter adapter;
    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider((ViewModelStoreOwner) mContext).get(HomeViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        binding.setActivity(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        binding.homeRecyclerview.setLayoutManager(linearLayoutManager);
        adapter = new HomeAdapter(mContext);
        binding.homeRecyclerview.setAdapter(adapter);

        Log.e("test","a123a");
        if (viewModel.getProductArray().size() <= 0)
            getPost();

        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            viewModel.productArray.clear();
            getPost();
            binding.swipeRefreshLayout.setRefreshing(false);
        });

        return binding.getRoot();
    }

    protected void getPost() {
        db = FirebaseFirestore.getInstance();
        db.collection("post")
                .document("information")
                .get()
                .addOnCompleteListener(task -> {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    try {
                        int postNumber = Integer.parseInt(String.valueOf(documentSnapshot.get("postNumbers")));
                        SetHomePostAsyncTask setHomePostAsyncTask = new SetHomePostAsyncTask(adapter, mContext, binding, postNumber);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                            setHomePostAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        } else {
                            setHomePostAsyncTask.execute();
                        }
                    } catch (NullPointerException e) {

                    }
                });
    }

    public void go_searchActivity(View view) {
        Intent intent = new Intent(mContext, SearchActivity.class);
        startActivity(intent);
    }

    public void go_productWriting(View view) {
        Intent intent = new Intent(mContext, ProductWriting.class);
        startActivity(intent);
    }

}