package com.example.village.screen.productwriting;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;

import com.example.village.databinding.ActivityProductWritingBinding;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProductWritingHelper {

    private Context context;
    private DisplayMetrics displayMetrics;
    private ActivityProductWritingBinding binding;
    private ProductWriting productWriting;
    private ProductViewModel viewModel;

    public ProductWritingHelper(Context context, DisplayMetrics displayMetrics, ProductViewModel viewModel, ActivityProductWritingBinding binding, ProductWriting productWriting) {
        this.context = context;
        this.displayMetrics = displayMetrics;
        this.viewModel = viewModel;
        this.binding = binding;
        this.productWriting = productWriting;
    }

    protected void setUi() {
        ArrayList<String> period = new ArrayList<>();
        period.add("월");
        period.add("주");
        period.add("일");
        SpinnerAdapter adapter = new SpinnerAdapter(productWriting.getApplicationContext(), period);
        binding.periodSpinner.setAdapter(adapter);
    }

    protected String toComma(String str) {
        Double value = Double.parseDouble(str);
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        return decimalFormat.format(value);
    }

    public boolean isNotNullValue() {

        if (viewModel.arrayList == null || viewModel.arrayList.size() <= 0) {

            Dialog dialog = new com.example.village.util.Dialog(context,displayMetrics, "상품등록", "사진을 추가해주세요.");
            dialog.getWindow().setGravity(Gravity.CENTER);
            dialog.show();
            return false;
        }

        if (binding.productNameEtv.getText().toString().equals("")) {
            Dialog dialog = new com.example.village.util.Dialog(context,displayMetrics, "상품등록", "제목을 입력해주세요.");
            dialog.getWindow().setGravity(Gravity.CENTER);
            dialog.show();
            return false;
        } else if (binding.priceEtv.getText().toString().equals("")) {
            Dialog dialog = new com.example.village.util.Dialog(context,displayMetrics, "상품등록", "가격을 입력해주세요.");
            dialog.getWindow().setGravity(Gravity.CENTER);
            dialog.show();
            return false;
        } else if (binding.hashtagEtv.getText().toString().equals("")) {
            Dialog dialog = new com.example.village.util.Dialog(context,displayMetrics, "상품등록", "연관해시태그를 입력해주세요.");
            dialog.getWindow().setGravity(Gravity.CENTER);
            dialog.show();
            return false;
        } else if (binding.descriptionEtv.getText().toString().equals("")) {
            Dialog dialog = new com.example.village.util.Dialog(context,displayMetrics, "상품등록", "상품설명을 입력해주세요.");
            dialog.getWindow().setGravity(Gravity.CENTER);
            dialog.show();
            return false;
        }
        return true;
    }

}
