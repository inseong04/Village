package com.example.village.screen.chating.account;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import com.example.village.R;
import com.example.village.databinding.ActivityAccountModifyBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class AccountModifyActivity extends AppCompatActivity {

    private ActivityAccountModifyBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_account_modify);
        Log.e("test", binding.amEtv1.getText().toString());
        String text = getIntent().getBooleanExtra("modify", false) ? "계좌를 변경합니다" : "계좌를 등록합니다";
        binding.setTvText(text);

        AccountModifyHelper accountModifyHelper = new AccountModifyHelper(this,
                getResources().getDisplayMetrics(), binding);

        binding.amBtn1.setOnClickListener(v -> {
            if (accountModifyHelper.isNotNull()) {

                Thread thread = new Thread(
                        () -> {

                            Map<String, Object> map = new HashMap<>();
                            map.put("accountBank", binding.amEtv1.getText().toString());
                            map.put("accountNumber", binding.amEtv2.getText().toString());
                            map.put("accountName", binding.amEtv3.getText().toString());
                            FirebaseFirestore.getInstance().collection("users")
                                    .document(FirebaseAuth.getInstance().getUid())
                                    .update(map);
                        });
                thread.start();

                Intent resultIntent = new Intent();
                resultIntent.putExtra("accountBank", binding.amEtv1.getText().toString());
                resultIntent.putExtra("accountNumber", binding.amEtv2.getText().toString());
                resultIntent.putExtra("accountName", binding.amEtv3.getText().toString());
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        binding.amEtv1.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                return keyCode == KeyEvent.KEYCODE_ENTER;
            }
        });

        binding.amEtv2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                return keyCode == KeyEvent.KEYCODE_ENTER;
            }
        });

        binding.amEtv3.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                return keyCode == KeyEvent.KEYCODE_ENTER;
            }
        });
    }
}