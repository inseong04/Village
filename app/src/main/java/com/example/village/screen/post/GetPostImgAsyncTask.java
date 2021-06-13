package com.example.village.screen.post;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;

import com.example.village.R;
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
    int i=0;

    public GetPostImgAsyncTask(Context mContext, PostViewModel viewModel, ActivityPostBinding binding, int postNumber, int imageNumber) {
        this.mContext = mContext;
        this.viewModel = viewModel;
        this.binding = binding;
        this.postNumber = postNumber;
        this.imageNumber = imageNumber;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.storageReference = FirebaseStorage.getInstance().getReference();
    }

    @Override
    protected Object doInBackground(Object[] objects) {

        if (i < imageNumber) {
            Log.e("download","postImg/" + "img" + "-" + postNumber + "-" + i);
            storageReference.child("postImg/" + "img" + "-" + postNumber + "-" + i).getDownloadUrl().
                    addOnSuccessListener(uri -> {
                        viewModel.uriArrayList.add(uri);
                        i++;
                        doInBackground(null);
                    });
        }
        else {
            publishProgress(null);
        }



        return null;
    }

    @Override
    protected void onProgressUpdate(Object[] values) {
        super.onProgressUpdate(values);
        binding.viewPager2.setOffscreenPageLimit(1);
        binding.viewPager2.setAdapter(new PostPagerAdapter(viewModel));
        setupIndicators(viewModel.uriArrayList.size());
    }

    private void setupIndicators(int count) {
        ImageView[] indicators = new ImageView[count];
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        params.setMargins(16, 8, 16, 8);

        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(mContext);
            indicators[i].setImageDrawable(ContextCompat.getDrawable(mContext,
                    R.drawable.indicatior_not_focus));
            indicators[i].setLayoutParams(params);

            binding.layoutIndicator.addView(indicators[i]);
        }
        setCurrentIndicator(0);
    }

    private void setCurrentIndicator(int position) {
        int childCount = binding.layoutIndicator.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) binding.layoutIndicator.getChildAt(i);
            if (i == position) {
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        mContext,
                        R.drawable.indicatior_focus
                ));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        mContext,
                        R.drawable.indicatior_not_focus
                ));
            }
        }
    }
}
