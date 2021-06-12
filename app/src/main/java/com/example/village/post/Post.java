package com.example.village.post;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.viewpager2.widget.ViewPager2;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.village.GetTime;
import com.example.village.R;
import com.example.village.databinding.ActivityPostBinding;
import com.example.village.home.HomeViewModel;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

public class Post extends AppCompatActivity {

    private ActivityPostBinding binding;
    FirebaseFirestore db;
    LinearLayout layoutIndicator;
    PostViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_post);
        final String postNumber = String.valueOf(getIntent().getIntExtra("postNumber",1));
        db = FirebaseFirestore.getInstance();
        viewModel = new ViewModelProvider(this).get(PostViewModel.class);


        db.collection("post")
                .document(postNumber)
                .get()
                .addOnCompleteListener(task -> {
                    DocumentSnapshot documentSnapshot = task.getResult();

                    PostViewModel viewModel = new ViewModelProvider(this).get(PostViewModel.class);
                    int imageNumber = Integer.parseInt(String.valueOf(documentSnapshot.get("imageNumber")));
                    GetPostImgThread getPostImgThread = new GetPostImgThread(viewModel, Integer.parseInt(postNumber), imageNumber);
                    getPostImgThread.start();
/*                    GetPostImgAsyncTask getPostImgAsyncTask = new GetPostImgAsyncTask(getApplicationContext(), binding,Integer.parseInt(postNumber), imageNumber);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                        getPostImgAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                    } else {
                        getPostImgAsyncTask.execute();
                    }*/
                    binding.setUserName(String.valueOf(documentSnapshot.get("userName")));
                    binding.setTitle(String.valueOf(documentSnapshot.get("productName")));
                    binding.setDescription(String.valueOf(documentSnapshot.get("description")));
                    binding.setHashTag(String.valueOf(documentSnapshot.get("hashTag")));
                    binding.setPeriod(String.valueOf(documentSnapshot.get("period")));
                    binding.setPrice(String.valueOf(documentSnapshot.get("price")));
                    binding.setTime(GetTime.getTime());

                    try {
                        Log.i("zxcvzcxv","join");
                        getPostImgThread.join();
                        Log.i("her","after join");
                    } catch (InterruptedException e) {
                        Log.e("error",e.toString());
                        e.printStackTrace();
                    }
                    Log.e("agwe","run");
                    binding.viewPager2.setOffscreenPageLimit(1);
                    binding.viewPager2.setAdapter(new PostPagerAdapter(viewModel));
                    Log.e("view",String.valueOf(viewModel.uriArrayList.size()));
                    setupIndicators(1);
                });

        binding.viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentIndicator(position);
            }
        });
        Log.e("size",String.valueOf(viewModel.uriArrayList.size()));


    }

    private void setupIndicators(int count) {
        ImageView[] indicators = new ImageView[count];
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        params.setMargins(16, 8, 16, 8);

        try {
            for (int i = 0; i < indicators.length; i++) {
                indicators[i] = new ImageView(this);
                indicators[i].setImageDrawable(ContextCompat.getDrawable(this,
                        R.drawable.indicatior_not_focus));
                indicators[i].setLayoutParams(params);
                Log.e("indi", String.valueOf((indicators[i] == null)));
                layoutIndicator.addView(indicators[i]);
            }
            setCurrentIndicator(0);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void setCurrentIndicator(int position) {
        int childCount = layoutIndicator.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) layoutIndicator.getChildAt(i);
            if (i == position) {
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        this,
                        R.drawable.indicatior_focus
                ));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        this,
                        R.drawable.indicatior_not_focus
                ));
            }
        }
    }
}