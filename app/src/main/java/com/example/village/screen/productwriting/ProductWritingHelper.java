package com.example.village.screen.productwriting;

import com.example.village.databinding.ActivityProductWritingBinding;
import com.example.village.util.WarningDialogFragment;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProductWritingHelper {

    private static String title = "상품등록";
    private ActivityProductWritingBinding binding;
    private ProductWriting productWriting;
    private ProductViewModel viewModel;

    public ProductWritingHelper(ProductViewModel viewModel, ActivityProductWritingBinding binding, ProductWriting productWriting) {
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
            WarningDialogFragment warningDialogFragment = new WarningDialogFragment(title, "사진을 추가해주세요.");
            warningDialogFragment.show(productWriting.getSupportFragmentManager(), "warningDialog");
            return false;
        }

        if (binding.productNameEtv.getText().toString().equals("")) {
            WarningDialogFragment warningDialogFragment = new WarningDialogFragment(title, "제목을 입력해주세요.");
            warningDialogFragment.show(productWriting.getSupportFragmentManager(), "warningDialog");
            return false;
        } else if (binding.priceEtv.getText().toString().equals("")) {
            WarningDialogFragment warningDialogFragment = new WarningDialogFragment(title, "가격을 입력해주세요.");
            warningDialogFragment.show(productWriting.getSupportFragmentManager(), "warningDialog");
            return false;
        } else if (binding.hashtagEtv.getText().toString().equals("")) {
            WarningDialogFragment warningDialogFragment = new WarningDialogFragment(title, "연관해시태그를 입력해주세요.");
            warningDialogFragment.show(productWriting.getSupportFragmentManager(), "warningDialog");
            return false;
        } else if (binding.descriptionEtv.getText().toString().equals("")) {
            WarningDialogFragment warningDialogFragment = new WarningDialogFragment(title, "상품설명을 입력해주세요.");
            warningDialogFragment.show(productWriting.getSupportFragmentManager(), "warningDialog");
            return false;
        }
        return true;
    }
}
