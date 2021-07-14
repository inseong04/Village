package com.example.village.screen.home.searchresult;

import android.content.Context;
import android.util.Log;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class Search {

    final Context mContext;
    final private SearchResultViewModel viewModel;
    final private FirebaseFirestore db;
    final private String searchWord;
    final private int postNumber;

    public Search(Context mContext, SearchResultViewModel viewModel, String searchWord, int postNumber) {
        this.mContext = mContext;
        this.viewModel = viewModel;
        this.searchWord = searchWord;
        this.postNumber = postNumber;
        db = FirebaseFirestore.getInstance();

        ArrayList<Integer> matchingPostNum = new ArrayList<>();
        db.collection("post")
                .document("information")
                .get()
                .addOnCompleteListener(task -> {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    for (int i = 1; i < postNumber; i++) {
                        String title = String.valueOf(documentSnapshot.get("post-" + i));
                        if (title.contains(searchWord)) {

                            matchingPostNum.add(i);
                        }
                    }


                });

    }
}
