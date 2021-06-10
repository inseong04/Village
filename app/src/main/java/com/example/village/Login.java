package com.example.village;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.village.databinding.ActivityLoginBinding;
import com.example.village.rdatabase.LoginData;
import com.example.village.rdatabase.LoginDatabase;
import com.example.village.signup.Name;
import com.example.village.splash.Splash;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.net.ContentHandler;
import java.util.ArrayList;

public class Login extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }

    protected void onResume() {
        super.onResume();
        // Issue : Layout이 먼저 보여짐
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final LoginDatabase db = Room.databaseBuilder(this, LoginDatabase.class,
                "village-login-db")
                .allowMainThreadQueries()
                .build();

        binding.loginBtn.setOnClickListener(v -> {
            String email = binding.etvId.getText().toString();
            String password = binding.etvPassword.getText().toString();

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("firebase", "signInWithEmail:success");
                                insertDB(db, email, password);
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.e("firebase", "signInWithEmail:failure", task.getException());

                                // ...
                            }

                            // ...
                        }
                    });

        });

        binding.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Name.class);
                startActivity(intent);
            }
        });
    }

    private void insertDB(LoginDatabase db, String id, String password) {
        Log.w("Login::Room", "insertDB");
        db.LoginDataDao().insertLogin(new LoginData(id, password));
    }


}