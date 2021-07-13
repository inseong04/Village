package com.example.village.screen.chating.account;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.village.R;
import com.example.village.databinding.ActivityAccountBinding;
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
        Intent intent = new Intent(getApplicationContext(), AccountModifyActivity.class);
        FirebaseFirestore.getInstance().collection("users")
                .document(uid)
                .get()
                .addOnCompleteListener(task -> {
                    DocumentSnapshot documentSnapshot = task.getResult();

                    if (documentSnapshot.get("accountName") != null && !documentSnapshot.get("accountName").toString().equals("")) {
                        intent.putExtra("modify", true);
                        binding.setAccountName((String) documentSnapshot.get("accountName"));
                        binding.setAccountNumber((String) documentSnapshot.get("accountNumber"));
                        binding.setAccountBank((String) documentSnapshot.get("accountBank"));
                        binding.accountBtn1.setVisibility(View.VISIBLE);
                        binding.accountTv2.setVisibility(View.VISIBLE);
                        binding.accountTv3.setVisibility(View.VISIBLE);
                        binding.accountTv4.setVisibility(View.VISIBLE);
                        binding.accountTv5.setVisibility(View.VISIBLE);
                        binding.accountTv6.setVisibility(View.VISIBLE);
                        binding.accountTv7.setVisibility(View.VISIBLE);
                        binding.accountView1.setVisibility(View.VISIBLE);
                        binding.accountView2.setVisibility(View.VISIBLE);
                    }
                    else {
                        intent.putExtra("modify", false);

                        binding.accountBtn3.setVisibility(View.VISIBLE);

                    }
                });

        binding.accountBtn1.setOnClickListener(v -> {
            startActivityForResult(intent, 100);
        });

        binding.accountBtn3.setOnClickListener(v -> {
            startActivityForResult(intent, 100);
        });

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
            binding.accountBtn1.setVisibility(View.VISIBLE);
            binding.accountBtn3.setVisibility(View.INVISIBLE);
            binding.accountTv2.setVisibility(View.VISIBLE);
            binding.accountTv3.setVisibility(View.VISIBLE);
            binding.accountTv4.setVisibility(View.VISIBLE);
            binding.accountTv5.setVisibility(View.VISIBLE);
            binding.accountTv6.setVisibility(View.VISIBLE);
            binding.accountTv7.setVisibility(View.VISIBLE);
            binding.accountView1.setVisibility(View.VISIBLE);
            binding.accountView2.setVisibility(View.VISIBLE);
        }
    }
}