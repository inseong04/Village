package com.example.village.home;

import android.view.View;

import androidx.databinding.BindingAdapter;

import com.google.common.util.concurrent.ExecutionError;

import java.util.EmptyStackException;
import java.util.concurrent.ExecutionException;

public class HomeBindingAdapter {

    @BindingAdapter("visible")
    public static void setVisible(View view,Boolean isVisible) {
            if (isVisible)
                view.setVisibility(view.VISIBLE);
            else
                view.setVisibility(view.GONE);

        }

}

