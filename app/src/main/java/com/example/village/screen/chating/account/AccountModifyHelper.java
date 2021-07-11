package com.example.village.screen.chating.account;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import com.example.village.databinding.ActivityAccountModifyBinding;

public class AccountModifyHelper {
    private Context context;
    private DisplayMetrics displayMetrics;
    private ActivityAccountModifyBinding binding;

    AccountModifyHelper (Context context, DisplayMetrics displayMetrics, ActivityAccountModifyBinding binding) {
        this.context = context;
        this.displayMetrics = displayMetrics;
        this.binding = binding;
    }

    protected boolean isNotNull () {

        if (binding.amEtv1.getText().equals("")) {
            Dialog dialog = new com.example.village.util.Dialog(context,displayMetrics, "계좌등록", "은행을 입력해주세요.");
            dialog.getWindow().setGravity(Gravity.CENTER);
            dialog.show();
            return false;
        }

        else if (binding.amEtv2.getText().equals("")) {
            Dialog dialog = new com.example.village.util.Dialog(context,displayMetrics, "계좌등록", "계좌번호를 입력해주세요.");
            dialog.getWindow().setGravity(Gravity.CENTER);
            dialog.show();
            return false;
        }

        else if (binding.amEtv3.getText().equals("")) {
            Dialog dialog = new com.example.village.util.Dialog(context,displayMetrics, "계좌등록", "예금주를 입력해주세요.");
            dialog.getWindow().setGravity(Gravity.CENTER);
            dialog.show();
            return false;
        }

        return true;
    }
}
