package com.example.village.screen.productwriting;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class ProductViewModel extends ViewModel {
    protected ArrayList<ProductData> arrayList = new ArrayList<>();
    protected MutableLiveData<String> hashtagEtvText;

    public String getHashtagEtvText() {
        return hashtagEtvText.getValue();
    }

    public ProductViewModel (){
        this.hashtagEtvText = new MutableLiveData<>();
    }
}
