package com.example.village.screen.chating.location;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.village.R;
import com.example.village.databinding.ActivityLocationBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class LocationActivity extends AppCompatActivity {

    private ActivityLocationBinding binding;
    private String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_location);
        uid = FirebaseAuth.getInstance().getUid();

        FirebaseFirestore.getInstance().collection("users")
                .document(uid)
                .get()
                .addOnCompleteListener(task -> {
                            DocumentSnapshot documentSnapshot = task.getResult();

                            if (documentSnapshot.get("detailLocation") != null) {
                                binding.setDetailLocation((String) documentSnapshot.get("detailLocation"));
                                binding.setLocationName((String) documentSnapshot.get("locationName"));
                                binding.setLocationContent((String) documentSnapshot.get("locationContent"));
                            } else {
                                binding.locationBtn3.setVisibility(View.VISIBLE);
                                binding.locationBtn1.setVisibility(View.INVISIBLE);
                                binding.locationTv2.setVisibility(View.INVISIBLE);
                                binding.locationTv3.setVisibility(View.INVISIBLE);
                                binding.locationTv4.setVisibility(View.INVISIBLE);
                                binding.locationTv5.setVisibility(View.INVISIBLE);
                                binding.locationView1.setVisibility(View.INVISIBLE);
                                binding.locationView2.setVisibility(View.INVISIBLE);
                                binding.locationView3.setVisibility(View.INVISIBLE);
                            }
                        });

        binding.locationBtn1.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), LocationModifyActivity.class);
            startActivityForResult(intent, 100);
        });

        binding.locationBtn3.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), LocationModifyActivity.class);
            startActivityForResult(intent, 100);
        });

        binding.locationBtn2.setOnClickListener(v -> {
            Intent resuliIntent = new Intent();
            resuliIntent.putExtra("locationName", binding.getLocationName());
            resuliIntent.putExtra("detailLocation", binding.getDetailLocation());
            resuliIntent.putExtra("locationContent", binding.getLocationContent());
            setResult(RESULT_OK, resuliIntent);
            finish();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK) {
            binding.setLocationName(data.getStringExtra("locationName"));
            binding.setDetailLocation(data.getStringExtra("detailLocation"));
            binding.setLocationContent(data.getStringExtra("locationContent"));
        }
    }

}