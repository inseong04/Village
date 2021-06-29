package com.example.village.screen.chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.village.R;
import com.example.village.databinding.ActivityChatingBinding;

public class Chating extends AppCompatActivity {

    private ActivityChatingBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chating);
        binding.setActivity(this);


    }
}