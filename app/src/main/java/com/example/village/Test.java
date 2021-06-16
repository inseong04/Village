package com.example.village;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.village.screen.login.Login;

public class Test extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        findViewById(R.id.goLogin).setOnClickListener(v ->{
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
            finish();
        });

    }
}