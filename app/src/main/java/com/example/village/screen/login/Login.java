package com.example.village.screen.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.example.village.screen.MainActivity;
import com.example.village.databinding.ActivityLoginBinding;
import com.example.village.rdatabase.LoginData;
import com.example.village.rdatabase.LoginDatabase;
import com.example.village.screen.signup.SignupActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

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
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final LoginDatabase db = Room.databaseBuilder(this, LoginDatabase.class,
                "village-login-db")
                .allowMainThreadQueries()
                .build();
        try {
            binding.loginBtn.setOnClickListener(v -> {

                String email = binding.etvId.getText().toString();
                String password = binding.etvPassword.getText().toString();
                if (email.equals("") || email == null)
                    errorAlarm("아이디를 입력해주세요.");
                else if (password.equals("") || password == null)
                    errorAlarm("비밀번호를 입력해주세요.");
                else {
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        Log.d("firebase", "signInWithEmail:success");
                                        insertDB(db, email, password);
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        errorAlarm("존재하지 않는 아이디이거나, 잘못된 비밀번호입니다.");
                                    }
                                }
                            });
                }
            });

            binding.btnSignup.setOnClickListener(v -> {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
            });

            binding.etvId.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    return keyCode == KeyEvent.KEYCODE_ENTER;
                }
            });

            binding.etvPassword.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    return keyCode == KeyEvent.KEYCODE_ENTER;
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void errorAlarm(String text) {
        binding.setWarning(text);
        binding.errorText.setVisibility(View.VISIBLE);
    }

    private void insertDB(LoginDatabase db, String id, String password) {
        db.LoginDataDao().insertLogin(new LoginData(id, password));
    }


}