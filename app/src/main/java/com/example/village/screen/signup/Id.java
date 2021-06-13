package com.example.village.screen.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.village.databinding.ActivityIdBinding;

public class Id extends AppCompatActivity {

    private ActivityIdBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIdBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent1 = getIntent();
        String name = intent1.getExtras().getString("name");

        binding.btnIdNext.setOnClickListener(v -> {
                Intent intent = new Intent(getApplicationContext(), Password.class);
                intent.putExtra("name",name);
                intent.putExtra("email",binding.etvId.getText().toString().trim());
                startActivity(intent);
                finish();
        });

    }
}