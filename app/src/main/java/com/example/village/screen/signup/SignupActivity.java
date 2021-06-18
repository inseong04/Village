package com.example.village.screen.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.village.R;

public class SignupActivity extends AppCompatActivity {

    SignupFragment1 signupFragment1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        signupFragment1 = new SignupFragment1();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment, signupFragment1)
                .commit();
    }


}