package com.example.village.screen.chating;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.WindowManager;

import androidx.fragment.app.FragmentManager;

import com.example.village.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ChatRentalEndDialog extends Dialog {
    private Context context;
    private String postNumber;
    private DisplayMetrics displayMetrics;
    private FragmentManager fragmentManager;
    private String[] rentalProduct;
    ChatRentalEndDialog (Context context, FragmentManager fragmentManager, DisplayMetrics displayMetrics,
                         String postNumber, String[] rentalProduct) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.displayMetrics = displayMetrics;
        this.postNumber = postNumber;
        this.rentalProduct = rentalProduct;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        params.dimAmount = 0.5f;
        params.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 254, displayMetrics);
        params.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 144, displayMetrics);
        getWindow().setAttributes(params);
        setContentView(R.layout.chat_rentalend_dialog);

        findViewById(R.id.rentalEndBtn1).setOnClickListener(v -> {
            Map<String, Object> map = new HashMap<>();
            map.put("rental", false);
            FirebaseFirestore.getInstance().collection("post")
                    .document(postNumber)
                    .update(map)
                    .addOnCompleteListener(task -> {
                        StringBuilder rentalProduct2 = new StringBuilder();
                        for (int i=0; i<rentalProduct.length; i++) {
                            if (rentalProduct[i].equals(postNumber))
                                continue;
                            rentalProduct2.append(rentalProduct[i]);
                        }
                        Thread thread = new Thread(
                                () -> {
                                    Map<String, Object> map1 = new HashMap<>();
                                    map1.put("rentalProduct", rentalProduct2.toString());
                                   FirebaseFirestore.getInstance().collection("users")
                                   .document(FirebaseAuth.getInstance().getUid())
                                   .update(map1);
                                });
                        thread.start();
                        Dialog dialog = new com.example.village.util.Dialog(context,displayMetrics, "대여하기", "대여가 종료되었습니다.");
                        dialog.getWindow().setGravity(Gravity.CENTER);
                        dialog.show();
                        dismiss();
                    });
        });

        findViewById(R.id.rentalEndBtn2).setOnClickListener(v -> {
            dismiss();
        });

    }
}
