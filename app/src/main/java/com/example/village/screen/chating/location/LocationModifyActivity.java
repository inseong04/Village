package com.example.village.screen.chating.location;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.example.village.R;
import com.example.village.databinding.ActivityLocationBinding;
import com.example.village.databinding.ActivityLocationModifyBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class LocationModifyActivity extends AppCompatActivity {

    private ActivityLocationModifyBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_location_modify);

        String text = getIntent().getBooleanExtra("modify", false) ? "배송지를 변경합니다" : "배송지를 등록합니다";
        binding.setTvText(text);

        LocationModifyHelper locationModifyHelper = new LocationModifyHelper(this,
                getResources().getDisplayMetrics(), binding);

        binding.lmBtn1.setOnClickListener(v -> {
            if (locationModifyHelper.isNotNull()) {

                Thread thread = new Thread(
                        () -> {

                            Map<String, Object> map = new HashMap<>();
                            map.put("locationName", binding.lmEtv1.getText().toString());
                            map.put("detailLocation", binding.lmEtv2.getText().toString());
                            // null이여도 되게 고쳐야함
                            map.put("locationContent", binding.lmEtv3.getText().toString());
                            FirebaseFirestore.getInstance().collection("users")
                                    .document(FirebaseAuth.getInstance().getUid())
                                    .update(map);
                        });
                thread.start();

                Intent resultIntent = new Intent();
                resultIntent.putExtra("locationName", binding.lmEtv1.getText().toString());
                resultIntent.putExtra("detailLocation", binding.lmEtv2.getText().toString());
                resultIntent.putExtra("locationContent", binding.lmEtv3.getText().toString());
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}