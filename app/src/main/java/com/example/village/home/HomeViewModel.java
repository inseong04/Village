package com.example.village.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {
    public MutableLiveData<Boolean> recentSearchVisible = new MutableLiveData<>();
    public MutableLiveData<Boolean> notFocusVisible = new MutableLiveData<>();
    public MutableLiveData<Boolean> focusVisible = new MutableLiveData<>();

    public HomeViewModel() {
        recentSearchVisible.setValue(false);
        notFocusVisible.setValue(true);
        focusVisible.setValue(false);
    }
}
