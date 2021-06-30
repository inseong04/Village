package com.example.village.screen.home;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.village.databinding.FragmentHomeBinding;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class SetHomePostAsyncTask extends AsyncTask {

    private HomeViewModel viewModel;
    private Context mContext;
    private FragmentHomeBinding binding;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    protected FirebaseFirestore db;
    String[] title;
    String[] location;
    String[] price;
    Uri[] postUri;
    Boolean[] rental;
    private int postNumber;
    HomeAdapter adapter;

    public SetHomePostAsyncTask(Context mContext, FragmentHomeBinding binding, int postNumber) {
        this.mContext = mContext;
        this.binding = binding;
        this.postNumber = postNumber;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        viewModel = new ViewModelProvider((ViewModelStoreOwner) mContext).get(HomeViewModel.class);
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        db = FirebaseFirestore.getInstance();
        title = new String[postNumber];
        location = new String[postNumber];
        price = new String[postNumber];
        postUri = new Uri[postNumber];
        rental = new Boolean[postNumber];

    }

    @Override
    protected Object doInBackground(Object[] objects) {

        for (int i = 1; i <= postNumber; i++) {
            int finalI = i;

            db.collection("post")
                    .document(String.valueOf(finalI))
                    .get()
                    .addOnCompleteListener(task -> {
                        int postNum = finalI;
                        DocumentSnapshot documentSnapshot = task.getResult();

                        title[finalI - 1] = String.valueOf(documentSnapshot.get("productName"));
                        location[finalI - 1] = String.valueOf(documentSnapshot.get("location"));
                        price[finalI - 1] = String.valueOf(documentSnapshot.get("price"));
                        rental[finalI - 1] = (Boolean) documentSnapshot.get("rental");

                        storageReference.child("postImg/" + "img" + "-" + finalI + "-0").getDownloadUrl().
                                addOnSuccessListener(uri -> {
                                    postUri[finalI - 1] = uri;
                                    PreviewPostData previewPostData = new PreviewPostData(postUri[finalI - 1], postNum, title[finalI - 1], location[finalI - 1], price[finalI - 1], rental[finalI - 1]);
                                    viewModel.productArray.add(previewPostData);
                                    publishProgress("");

                                });
                    });

        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Object[] values) {
        super.onProgressUpdate(values);
        adapter = new HomeAdapter(mContext);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        binding.homeRecyclerview.setLayoutManager(linearLayoutManager);
        Log.e("view", viewModel.getProductArray().toString());
        adapter.notifyDataSetChanged();
        binding.homeRecyclerview.setAdapter(adapter);
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);


    }


}
