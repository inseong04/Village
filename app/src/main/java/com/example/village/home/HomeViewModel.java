package com.example.village.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {
    public MutableLiveData<Boolean> visible = new MutableLiveData<>();

    public HomeViewModel() {
        visible.setValue(false);
    }

    public void setVisible() {
        visible.setValue(true);
    }

    public void setInvisible() {
        visible.setValue(false);
    }

}
