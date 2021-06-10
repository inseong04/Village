package com.example.village.home;

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

public class GetPostAsyncTask extends AsyncTask {

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
    private int postNumber;

    public GetPostAsyncTask(Context mContext, FragmentHomeBinding binding, int postNumber) {
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
    }

    @Override
    protected Object doInBackground(Object[] objects) {

        for(int i=1; i<=postNumber; i++) {
            int finalI = i;
            db.collection("post")
                    .document(String.valueOf(i) )
                    .get()
                    .addOnCompleteListener(task -> {

                        DocumentSnapshot documentSnapshot = task.getResult();
                        Log.e("z", "postinforrun");
                        title[finalI-1] = String.valueOf(documentSnapshot.get("productName"));
                        Log.e("title", String.valueOf(documentSnapshot.get("productName")));
                        location[finalI-1] = String.valueOf(documentSnapshot.get("location"));
                        Log.e("location", String.valueOf(documentSnapshot.get("location")));
                        price[finalI-1] = String.valueOf(documentSnapshot.get("price"));
                        Log.e("price", String.valueOf(documentSnapshot.get("price")));

                        storageReference.child("postImg/" + "img" + "-" + finalI + "-0").getDownloadUrl().addOnSuccessListener(uri -> {
                            Log.e("sucees", "getpostimage : "+uri.toString());
                            postUri[finalI-1] = uri;
                            HomeData homeData = new HomeData(postUri[finalI-1], title[finalI-1], location[finalI-1], price[finalI-1]);
                            Log.e("homeData", homeData.title+String.valueOf(finalI-1));
                            viewModel.productArray.add(homeData);
                        });
                    });

        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Object[] values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        HomeAdapter adapter = new HomeAdapter(mContext);
        binding.homeRecyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        binding.homeRecyclerview.setAdapter(adapter);
    }




}
