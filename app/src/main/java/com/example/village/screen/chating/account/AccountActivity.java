package com.example.village.screen.chating.account;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.village.R;
import com.example.village.databinding.ActivityAccountBinding;
import com.example.village.screen.chating.location.LocationModifyActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class AccountActivity extends AppCompatActivity {

    private ActivityAccountBinding binding;
    private String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_account);
        uid = FirebaseAuth.getInstance().getUid();

        FirebaseFirestore.getInstance().collection("users")
                .document(uid)
                .get()
                .addOnCompleteListener(task -> {
                    DocumentSnapshot documentSnapshot = task.getResult();

                    if (documentSnapshot.get("accountName") != null) {
                        binding.setAccountName((String) documentSnapshot.get("accountName"));
                        binding.setAccountNumber((String) documentSnapshot.get("accountNumber"));
                        binding.setAccountBank((String) documentSnapshot.get("accountBank"));
                    }
                });

        binding.accountBtn1.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), AccountModifyActivity.class);
            startActivityForResult(intent, 100);
        });

/*        binding.locationBtn3.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), LocationModifyActivity.class);
            startActivityForResult(intent, 100);
        });*/

        binding.accountBtn2.setOnClickListener(v -> {
            Intent resuliIntent = new Intent();
            resuliIntent.putExtra("accountName", binding.getAccountName());
            resuliIntent.putExtra("accountNumber", binding.getAccountNumber());
            resuliIntent.putExtra("accountBank", binding.getAccountBank());
            setResult(RESULT_OK, resuliIntent);
            finish();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK) {
            binding.setAccountName(data.getStringExtra("accountName"));
            binding.setAccountNumber(data.getStringExtra("accountNumber"));
            binding.setAccountBank(data.getStringExtra("accountBank"));
        }
    }
}