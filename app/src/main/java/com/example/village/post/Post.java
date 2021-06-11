package com.example.village.post;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.village.GetTime;
import com.example.village.R;
import com.example.village.databinding.ActivityPostBinding;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Post extends AppCompatActivity {

    private ActivityPostBinding binding;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_post);
        final String postNumber = getIntent().getExtras().getString("postNumber");
        db = FirebaseFirestore.getInstance();

        db.collection("post")
                .document(postNumber)
                .get()
                .addOnCompleteListener(task -> {
                    DocumentSnapshot documentSnapshot = task.getResult();

                    binding.setUserName(String.valueOf(documentSnapshot.get("userName")));
                    binding.setTitle(String.valueOf(documentSnapshot.get("title")));
                    binding.setDescription(String.valueOf(documentSnapshot.get("description")));
                    binding.setHashTag(String.valueOf(documentSnapshot.get("hashTag")));
                    binding.setPeriod(String.valueOf(documentSnapshot.get("period")));
                    binding.setPrice(String.valueOf(documentSnapshot.get("price")));
                    binding.setTime(GetTime.getTime());
                });

    }
}