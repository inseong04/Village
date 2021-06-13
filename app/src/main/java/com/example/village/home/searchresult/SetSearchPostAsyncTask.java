package com.example.village.home.searchresult;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.village.databinding.ActivitySearchResultBinding;
import com.example.village.databinding.FragmentHomeBinding;
import com.example.village.home.HomeAdapter;
import com.example.village.home.HomeViewModel;
import com.example.village.home.PreviewPostData;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class SetSearchPostAsyncTask extends AsyncTask {

    private SearchResultViewModel viewModel;
    private Context mContext;
    private ActivitySearchResultBinding binding;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    protected FirebaseFirestore db;
    String[] title;
    String[] location;
    String[] price;
    Uri[] postUri;
    private int postNumber;
    SearchResultAdapter adapter;

    public SetSearchPostAsyncTask(Context mContext, ActivitySearchResultBinding binding) {
        this.mContext = mContext;
        this.binding = binding;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.viewModel = new ViewModelProvider((ViewModelStoreOwner) mContext).get(SearchResultViewModel.class);
        this.postNumber = viewModel.matchingPostNum.size();
        this.firebaseStorage = FirebaseStorage.getInstance();
        this.storageReference = firebaseStorage.getReference();
        this.db = FirebaseFirestore.getInstance();
        this.title = new String[postNumber];
        this.location = new String[postNumber];
        this.price = new String[postNumber];
        this.postUri = new Uri[postNumber];

    }

    @Override
    protected Object doInBackground(Object[] objects) {

        Log.e("doIn","testtttttttttttttttttttttt");
        for (int i = 0; i < viewModel.matchingPostNum.size(); i++) {
            int finalI = i;
            Log.e("tesxxx","postImg/" + "img" + "-" + viewModel.matchingPostNum.get(i) + "-0");

            storageReference.child("postImg/" + "img" + "-" + viewModel.matchingPostNum.get(i) + "-0").getDownloadUrl().
                    addOnSuccessListener(uri -> {
                        postUri[finalI] = uri;
                        Log.e("test","success");

                        db.collection("post")
                                .document(String.valueOf(viewModel.matchingPostNum.get(finalI)))
                                .get()
                                .addOnCompleteListener(task -> {
                                        DocumentSnapshot documentSnapshot = task.getResult();
                                        int postNum = finalI;
                                        title[finalI] = String.valueOf(documentSnapshot.get("productName"));
                                        Log.e("title",title[finalI]);
                                        location[finalI] = String.valueOf(documentSnapshot.get("location"));
                                        Log.e("location",location[finalI]);
                                        price[finalI] = String.valueOf(documentSnapshot.get("price"));
                                        Log.e("price",price[finalI]);
                                        PreviewPostData previewPostData = new PreviewPostData(postUri[finalI], postNum, title[finalI], location[finalI], price[finalI]);
                                        viewModel.postArrayList.add(previewPostData);
                                        publishProgress("");
                                    });
                    });

        }
        return null;
    }

    @Override
    protected void onProgressUpdate (Object[] values) {
        super.onProgressUpdate(values);
        adapter = new SearchResultAdapter(mContext);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        binding.searchResultRecyclerView.setLayoutManager(linearLayoutManager);
        adapter.notifyDataSetChanged();
        binding.searchResultRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);


    }


}
