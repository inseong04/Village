package com.example.village.splash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.village.Login;
import com.example.village.MainActivity;
import com.example.village.R;
import com.example.village.rdatabase.LoginDatabase;
import com.example.village.rdatabase.UserDatabase;
import com.example.village.rdatabase.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Splash extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceStare) {
        super.onCreate(savedInstanceStare);
        setContentView(R.layout.activity_splash);
        final LoginDatabase db = Room.databaseBuilder(this, LoginDatabase.class,
                "village-login-db")
                .allowMainThreadQueries()
                .build();



        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if(db.LoginDataDao().RgetId() != null) {
                    String email = db.LoginDataDao().RgetId();
                    String password = db.LoginDataDao().RgetPassword();
                    final FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(Splash.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.w("firebase", "signInWithEmail:success");
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.e("firebase", "signInWithEmail:failure", task.getException());
                                        Intent intent = new Intent(getApplicationContext(), Login.class);
                                        startActivity(intent);
                                        finish();
                                        // ...
                                    }

                                    // ...
                                }
                            });
                }
                else {
                        Intent intent = new Intent(getApplicationContext(), Login.class);
                        startActivity(intent);
                        finish();
                    }
                }
        },3000);
    }
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

}