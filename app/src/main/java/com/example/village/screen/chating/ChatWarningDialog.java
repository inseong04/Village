package com.example.village.screen.chating;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.WindowManager;

import com.example.village.R;
import com.example.village.databinding.ActivityChatingBinding;

public class ChatWarningDialog extends Dialog {

    private Context context;
    private String title;
    private String postNumber;
    private DisplayMetrics displayMetrics;
    private ActivityChatingBinding binding;
    private ChatingViewModel viewModel;

    ChatWarningDialog (Context context, String title, String postNumber, DisplayMetrics displayMetrics,
                       ActivityChatingBinding binding, ChatingViewModel viewModel) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.context = context;
        this.title = title;
        this.postNumber = postNumber;
        this.displayMetrics = displayMetrics;
        this.binding = binding;
        this.viewModel = viewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        params.dimAmount = 0.5f;
        params.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 250, displayMetrics);
        params.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 400, displayMetrics);
        getWindow().setAttributes(params);
        setContentView(R.layout.chat_warning_dialog);

        findViewById(R.id.chatDialogTv3).setOnClickListener(v -> {
            dismiss();
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://cyberbureau.police.go.kr/mobile/sub/sub_02.jsp"));
            context.startActivity(intent);
        });

        findViewById(R.id.chatDialogBtn).setOnClickListener(v -> {
            dismiss();
                RentalDialog rentalDialog = new RentalDialog(context, displayMetrics, postNumber, title, binding, viewModel);
                rentalDialog.getWindow().setGravity(Gravity.CENTER);
                rentalDialog.show();

        });
    }
}
