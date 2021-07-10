package com.example.village.screen.post;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;

import com.example.village.screen.chating.Chating;
import com.example.village.util.GetTime;
import com.example.village.R;
import com.example.village.databinding.ActivityPostBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class Post extends AppCompatActivity {

    private ActivityPostBinding binding;
    FirebaseFirestore db;
    PostViewModel viewModel;
    private boolean rental;
    private boolean chatIntentRun;
    private String roomNumber;
    private String sellerUid;
    private String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_post);
        final String postNumber = String.valueOf(getIntent().getIntExtra("postNumber", 1));
        chatIntentRun = getIntent().getBooleanExtra("chatIntent", false);
        uid = FirebaseAuth.getInstance().getUid();
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
                        binding.rentalTv1.setTextColor(Color.parseColor("#000000"));
                        binding.rentalTv1.setBackground(ContextCompat.getDrawable(this, R.drawable.rental_true));
                    }
                    else {
                        binding.rentalTv1.setText("대여가능");
                        binding.rentalTv1.setBackground(ContextCompat.getDrawable(this, R.drawable.rental_false));
                    }
                    sellerUid = String.valueOf(documentSnapshot.get("sellerUid"));
                    roomNumber = String.valueOf(documentSnapshot.get("roomNumber"));
                    binding.setUserName(String.valueOf(documentSnapshot.get("name")));
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

        binding.callChatBtn.setOnClickListener(v -> {

            if (chatIntentRun) {
                finish();
            }
            else {

                if (uid.equals(sellerUid)) {
                    Dialog dialog = new com.example.village.util.Dialog(getApplicationContext(),getResources().getDisplayMetrics(), "채팅하기", "자신의 상품은 \n대여가 불가능합니다.");
                    dialog.getWindow().setGravity(Gravity.CENTER);
                    dialog.show();
                } else {

                    AtomicReference<Boolean> roomExistence = new AtomicReference<>(false);
                    db.collection("users")
                            .document(uid)
                            .get()
                            .addOnCompleteListener(task -> {

                                DocumentSnapshot documentSnapshot = task.getResult();

                                try {
                                    ArrayList<String> roomList = (ArrayList<String>) documentSnapshot.get("roomList");
                                    for (int i = 0; i < roomList.size(); i++) {
                                        String[] postAndRoomNumber = roomList.get(i).split("-");
                                        Log.e("test", postAndRoomNumber[1]);
                                        if (postAndRoomNumber[0].equals(postNumber) && Integer.parseInt(postAndRoomNumber[1])
                                                <= Integer.parseInt(roomNumber)) {
                                            String room = postNumber + "-" + postAndRoomNumber[1];
                                            Intent intent = new Intent(getApplicationContext(), Chating.class);
                                            intent.putExtra("sellerUid", sellerUid);
                                            intent.putExtra("postNumber", postNumber);
                                            intent.putExtra("roomNumber", room);
                                            startActivity(intent);
                                            roomExistence.set(true);
                                            break;
                                        }
                                    }
                                } catch (NullPointerException e) {
                                    String room = postNumber + "-" + roomNumber;
                                    Intent intent = new Intent(getApplicationContext(), Chating.class);
                                    intent.putExtra("sellerUid", sellerUid);
                                    intent.putExtra("postNumber", postNumber);
                                    intent.putExtra("roomNumber", room);
                                    startActivity(intent);
                                    Thread thread = new Thread(
                                            () -> {
                                                Map<String, Object> map2 = new HashMap<>();
                                                map2.put("roomNumber", Integer.parseInt(roomNumber) + 1);
                                                db.collection("post")
                                                        .document(postNumber)
                                                        .update(map2);
                                            }
                                    );
                                    thread.start();
                                    roomExistence.set(true);
                                }

                                if (!roomExistence.get()) {
                                    String room = postNumber + "-" + roomNumber;
                                    Intent intent = new Intent(getApplicationContext(), Chating.class);
                                    intent.putExtra("sellerUid", sellerUid);
                                    intent.putExtra("postNumber", postNumber);
                                    intent.putExtra("roomNumber", room);
                                    startActivity(intent);
                                    Thread thread = new Thread(
                                            () -> {
                                                Map<String, Object> map2 = new HashMap<>();
                                                map2.put("roomNumber", Integer.parseInt(roomNumber) + 1);
                                                db.collection("post")
                                                        .document(postNumber)
                                                        .update(map2);
                                            }
                                    );
                                    thread.start();
                                }
                            });
                }
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