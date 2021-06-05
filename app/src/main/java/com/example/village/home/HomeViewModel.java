package com.example.village.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class HomeViewModel extends ViewModel {
    public MutableLiveData<Boolean> recentSearchVisible = new MutableLiveData<>();
    public MutableLiveData<Boolean> notFocusVisible = new MutableLiveData<>();
    public MutableLiveData<Boolean> focusVisible = new MutableLiveData<>();
    public ArrayList<HomeData> arrayList;

    public ArrayList<HomeData> getArrayList() {
        return arrayList;
    }


    public HomeViewModel() {
        recentSearchVisible.setValue(false);
        notFocusVisible.setValue(true);
        focusVisible.setValue(false);
        arrayList = new ArrayList<>();
    }
}
