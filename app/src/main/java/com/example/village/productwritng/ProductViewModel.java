package com.example.village.productwritng;

import androidx.databinding.ObservableField;
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
