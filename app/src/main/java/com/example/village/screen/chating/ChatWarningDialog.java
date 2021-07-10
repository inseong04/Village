package com.example.village.screen.chating;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;

import androidx.fragment.app.FragmentManager;

import com.example.village.R;

public class ChatWarningDialog extends Dialog {

    private Context context;
    private FragmentManager fragmentManager;
    private Chating chating;
    private String title;
    private String postNumber;
    private DisplayMetrics displayMetrics;

    ChatWarningDialog (Context context, FragmentManager fragmentManager, Chating chating,
                       String title, String postNumber, DisplayMetrics displayMetrics) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.chating = chating;
        this.title = title;
        this.postNumber = postNumber;
        this.displayMetrics = displayMetrics;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        params.dimAmount = 0.5f;
        params.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 250, displayMetrics);
        params.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 340, displayMetrics);
        getWindow().setAttributes(params);
        setContentView(R.layout.chat_warning_dialog);

        findViewById(R.id.chatDialogBtn).setOnClickListener(v -> {
            dismiss();
                PostRentalDialogFragment postRentalDialogFragment = new PostRentalDialogFragment(context, chating, title, postNumber);
                postRentalDialogFragment.show(fragmentManager, "postRentalDialog");

        });
    }
}
