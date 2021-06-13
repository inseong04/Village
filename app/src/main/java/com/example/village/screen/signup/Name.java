package com.example.village.screen.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.village.databinding.ActivityNameBinding;

public class Name extends AppCompatActivity {

    private ActivityNameBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnNameNext.setOnClickListener(v -> {
                Intent intent = new Intent(getApplicationContext(), Id.class);
                intent.putExtra("name",binding.etvName.getText().toString().trim());
                startActivity(intent);
                finish();
        });
    }
}