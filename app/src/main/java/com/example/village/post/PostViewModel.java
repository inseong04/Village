package com.example.village.post;

import android.net.Uri;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class PostViewModel extends ViewModel {
    ArrayList<Uri> uriArrayList;

    public PostViewModel() {
        this.uriArrayList = new ArrayList<>();
    }
}
