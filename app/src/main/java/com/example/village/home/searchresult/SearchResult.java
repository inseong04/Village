package com.example.village.home.searchresult;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.village.R;
import com.example.village.databinding.ActivitySearchResultBinding;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class SearchResult extends AppCompatActivity {

    private ActivitySearchResultBinding binding;
    Context mContext;
    private SearchResultViewModel viewModel;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_result);
        binding.setActivity(this);
        String searchWord = getIntent().getStringExtra("searchWord");

        binding.setSearchWord(searchWord);
        mContext = this;
        viewModel = new ViewModelProvider(this).get(SearchResultViewModel.class);
        db = FirebaseFirestore.getInstance();
        db.collection("post")
                .document("information")
                .get()
                .addOnCompleteListener(task -> {
                   DocumentSnapshot documentSnapshot = task.getResult();
                   int postNumber = Integer.parseInt(String.valueOf(documentSnapshot.get("postNumbers")));
                    Search(mContext, viewModel, searchWord, postNumber);
                });

    }

    private void Search(Context mContext, SearchResultViewModel viewModel, String searchWord, int postNumber) {
        db = FirebaseFirestore.getInstance();

        db.collection("post")
                .document("information")
                .get()
                .addOnCompleteListener(task -> {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    for(int i=1; i<postNumber; i++) {
                        String title = String.valueOf(documentSnapshot.get("post-"+i));
                        if(title.contains(searchWord)) {
                            viewModel.matchingPostNum.add(i);

                        }
                    }

                    SetSearchPostAsyncTask setSearchPostAsyncTask = new SetSearchPostAsyncTask(mContext, binding);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                        Log.e("awegwae","weewqwwwwwwwwwwwwawerbddawersghwrsbghwsdbhwsedgbwerdfweadsabfehwgderfvfbrth4eqgwedffgnrtesfehsfewfghmgdgrdteswf");
                        setSearchPostAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                    } else {
                        setSearchPostAsyncTask.execute();
                    }


                });

    }

    public void goHome(View view) {
        finish();
    }
}