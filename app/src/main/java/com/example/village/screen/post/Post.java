package com.example.village.screen.post;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.ImageView;

import com.example.village.util.GetTime;
import com.example.village.R;
import com.example.village.databinding.ActivityPostBinding;
import com.example.village.util.WarningDialogFragment;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Post extends AppCompatActivity {

    private ActivityPostBinding binding;
    FirebaseFirestore db;
    PostViewModel viewModel;
    private boolean rental;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_post);
        final String postNumber = String.valueOf(getIntent().getIntExtra("postNumber", 1));
        db = FirebaseFirestore.getInstance();
        viewModel = new ViewModelProvider(this).get(PostViewModel.class);

        binding.descriptionTv.setMovementMethod(new ScrollingMovementMethod());

        db.collection("post")
                .document(postNumber)
                .get()
                .addOnCompleteListener(task -> {
                    DocumentSnapshot documentSnapshot = task.getResult();

                    PostViewModel viewModel = new ViewModelProvider(this).get(PostViewModel.class);
                    int imageNumber = Integer.parseInt(String.valueOf(documentSnapshot.get("imageNumber")));
                    GetPostImgAsyncTask getPostImgAsyncTask = new GetPostImgAsyncTask(this, viewModel, binding, Integer.parseInt(postNumber), imageNumber);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                        getPostImgAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                    } else {
                        getPostImgAsyncTask.execute();
                    }
                    rental = (boolean)documentSnapshot.get("rental");
                    if(rental) {
                        binding.rentalTv1.setText("대여중");
                        binding.rentalTv1.setBackground(ContextCompat.getDrawable(this, R.drawable.rental_true));
                    }
                    else {
                        binding.rentalTv1.setText("대여가능");
                        binding.rentalTv1.setBackground(ContextCompat.getDrawable(this, R.drawable.rental_false));
                    }

                    binding.setTitle(String.valueOf(documentSnapshot.get("productName")));
                    binding.setDescription(String.valueOf(documentSnapshot.get("description")));
                    binding.setHashTag(String.valueOf(documentSnapshot.get("hashTag")));
                    binding.setPeriod(String.valueOf(documentSnapshot.get("period")));
                    binding.setPrice(String.valueOf(documentSnapshot.get("price")));
                    binding.setTime(GetTime.getTime(Long.parseLong(String.valueOf(documentSnapshot.get("productTime")))));

                });

        binding.viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentIndicator(position);
            }
        });

        binding.rentalBtn.setOnClickListener(v -> {

            if (rental) {
                WarningDialogFragment warningDialogFragment = new WarningDialogFragment("대여하기", "이미 대여중인 상품입니다.");
                warningDialogFragment.show(getSupportFragmentManager(), "dialogFragment");
            } else {
                PostRentalDialogFragment postRentalDialogFragment = new PostRentalDialogFragment(this, binding.getTitle(), postNumber);
                postRentalDialogFragment.show(getSupportFragmentManager(), "postRentalDialog");
            }
        });

    }



    private void setCurrentIndicator(int position) {
        int childCount = binding.layoutIndicator.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) binding.layoutIndicator.getChildAt(i);
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