package com.example.village.signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.village.Login;
import com.example.village.R;
import com.example.village.databinding.ActivityPasswordBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Password extends AppCompatActivity {

    private ActivityPasswordBinding binding;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent1 = getIntent();
        String name = intent1.getExtras().getString("name");
        String email = intent1.getExtras().getString("email");

        mAuth = FirebaseAuth.getInstance();
        binding.btnPasswordNext.setOnClickListener(v -> {

            String password = binding.etvPassword.getText().toString();
            Log.e("aa", "email : " + email + "password : " + password);
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(Password.this, new OnCompleteListener<AuthResult>() {
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("firebase", "createUserWithEmail:success");
                                save_username(mAuth, name);
                                Intent intent = new Intent(getApplicationContext(), Login.class);
                                startActivity(intent);
                                finish();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("firebase", "createUserWithEmail:failure", task.getException());

                            }

                            // ...
                        }
                    });

        });
    }

    protected void save_username(FirebaseAuth mAuth,String name){
        final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        final String user_uid = user.getUid();
        Map<String,Object> map = new HashMap<>();
        map.put("name", name);
        firebaseFirestore.collection("users").document(user_uid)
                .set(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d("firebase","Successfully Document written.");
                    }
                });
    }
}