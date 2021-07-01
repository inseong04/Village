package com.example.village.screen.chating;

import android.app.Activity;
import android.os.AsyncTask;

import com.bumptech.glide.Glide;
import com.example.village.databinding.ActivityChatingBinding;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ChatingPostAsyncTask extends AsyncTask {

    private ActivityChatingBinding binding;
    private FirebaseFirestore db;
    private StorageReference storageReference;
    private String postNumber;
    private Activity parentActivity;

    public ChatingPostAsyncTask(Activity activity, ActivityChatingBinding binding, String postNumber) {
        parentActivity = activity;
        this.binding = binding;
        this.postNumber = postNumber;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        db = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
    }

    @Override
    protected Object doInBackground(Object[] objects) {

        db.collection("post")
                .document(postNumber)
                .get()
                .addOnCompleteListener(task -> {
                   if(task.isSuccessful()) {
                       DocumentSnapshot documentSnapshot = task.getResult();
                       String name = String.valueOf(documentSnapshot.get("name"));
                       String title = String.valueOf(documentSnapshot.get("productName"));
                       String price = String.valueOf(documentSnapshot.get("price"));
                       binding.setName(name);
                       binding.setTitle(title);
                       binding.setPrice(price);
                   }
                });

        storageReference.child("postImg/" + "img" + "-" + postNumber + "-" + 0).getDownloadUrl()
                .addOnSuccessListener(uri -> {
                    Glide.with(parentActivity)
                            .load(uri)
                            .into(binding.productIv);
                });

        return null;
    }
}
