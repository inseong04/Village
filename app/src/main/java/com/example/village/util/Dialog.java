package com.example.village.util;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.village.R;

public class Dialog extends android.app.Dialog {

    // required : context, displayMetrics, title, content

    private DisplayMetrics displayMetrics;
    private String title;
    private String content;

    public Dialog(@NonNull Context context, DisplayMetrics displayMetrics,
                  String title, String content) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.displayMetrics = displayMetrics;
        this.title = title;
        this.content = content;
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
        setContentView(R.layout.dialog);

        ((TextView)findViewById(R.id.dialogTv1)).setText(title);
        ((TextView)findViewById(R.id.dialogTv2)).setText(content);

        findViewById(R.id.dialogTv3).setOnClickListener(v -> {
            dismiss();
        });


    }
}