package com.example.village.screen.chating.location;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;

import com.example.village.databinding.ActivityLocationModifyBinding;

public class LocationModifyHelper {

    private Context context;
    private DisplayMetrics displayMetrics;
    private ActivityLocationModifyBinding binding;

    LocationModifyHelper (Context context, DisplayMetrics displayMetrics, ActivityLocationModifyBinding binding) {
        this.context = context;
        this.displayMetrics = displayMetrics;
        this.binding = binding;
    }

    protected boolean isNotNull() {

        if (binding.lmEtv1.getText().toString().equals("")) {
            Dialog dialog = new com.example.village.util.Dialog(context,displayMetrics, "위치등록", "받는사람을 입력해주세요.");
            dialog.getWindow().setGravity(Gravity.CENTER);
            dialog.show();
            return false;
        }

        else if (binding.lmEtv2.getText().toString().equals("")) {
            Dialog dialog = new com.example.village.util.Dialog(context,displayMetrics, "위치등록", "배송지를 입력해주세요.");
            dialog.getWindow().setGravity(Gravity.CENTER);
            dialog.show();
            return false;
        }

        else if (binding.lmEtv3.getText().toString().equals("")) {
            Dialog dialog = new com.example.village.util.Dialog(context,displayMetrics, "위치등록", "요청사항을 입력해주세요.");
            dialog.getWindow().setGravity(Gravity.CENTER);
            dialog.show();
            return false;
        }
        return true;
    }
}
