package com.example.village.screen.home;

import android.view.View;

import androidx.databinding.BindingAdapter;

public class HomeBindingAdapter {

    @BindingAdapter("visible")
    public static void setVisible(View view,Boolean isVisible) {
            if (isVisible)
                view.setVisibility(view.VISIBLE);
            else
                view.setVisibility(view.GONE);

        }

}

