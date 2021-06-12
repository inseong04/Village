package com.example.village.post;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.village.databinding.ActivityPostBinding;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class GetPostImgAsyncTask extends AsyncTask {

    private  ActivityPostBinding binding;
    Context mContext;
    PostViewModel viewModel;
    StorageReference storageReference;
    int postNumber;
    int imageNumber;

    public GetPostImgAsyncTask(Context mContext, ActivityPostBinding binding, int postNumber, int imageNumber) {
        this.mContext = mContext;
        this.binding = binding;
        this.postNumber = postNumber;
        this.imageNumber = imageNumber;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.viewModel = new ViewModelProvider((ViewModelStoreOwner) mContext).get(PostViewModel.class);
        this.storageReference = FirebaseStorage.getInstance().getReference();
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        for(int i=0; i< imageNumber; i++) {
            storageReference.child("postImg/" + "img" + "-" + postNumber + "-" + i).getDownloadUrl().
                    addOnSuccessListener(uri -> {
                        viewModel.uriArrayList.add(uri);
                    });
        }

        publishProgress("");

        return null;
    }

    @Override
    protected void onProgressUpdate(Object[] values) {
        super.onProgressUpdate(values);
        binding.viewPager2.setOffscreenPageLimit(viewModel.uriArrayList.size());
        binding.viewPager2.setAdapter(new PostPagerAdapter(viewModel));

    }
}
