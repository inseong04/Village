package com.example.village.home;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.village.R;
import com.example.village.databinding.FragmentHomeBinding;
import com.example.village.home.search.SearchActivity;
import com.example.village.productwritng.ProductWriting;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

public class Home extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewModel viewModel;
    protected FirebaseFirestore db;
    private Context mContext;
    public Home() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        mContext = context;
        getPost();
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

        binding.swipeRefreshLayout.setOnRefreshListener( () -> {
           // onRefresh
            viewModel.productArray.clear();
            getPost();
            binding.swipeRefreshLayout.setRefreshing(false);
        });

        return binding.getRoot();
    }

    @Override
        public void onDestroyView() {
        super.onDestroyView();
        viewModel.productArray.clear();
    }

    protected void getPost() {
        db = FirebaseFirestore.getInstance();
        db.collection("post")
                .document("information")
                .get()
                .addOnCompleteListener( task -> {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    try {
                        int postNumber = Integer.parseInt(String.valueOf(documentSnapshot.get("postNumbers")));
                        GetHomePostAsyncTask getHomePostAsyncTask = new GetHomePostAsyncTask(mContext, binding, postNumber);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                            getHomePostAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                        } else {
                            getHomePostAsyncTask.execute();
                        }
                    } catch (NullPointerException e) {

                    }
                });
    }

    public void go_searchActivity(View view) {
        Intent intent = new Intent(getContext(), SearchActivity.class);
        startActivity(intent);
    }

    public void go_productWriting(View view) {
        Intent intent = new Intent(getContext(), ProductWriting.class);
        startActivity(intent);
    }




}