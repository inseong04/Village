package com.example.village.util;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.village.R;
import com.example.village.databinding.FragmentDialogWarningBinding;


public class WarningDialogFragment extends DialogFragment {

    private String title;
    private String content;

    public WarningDialogFragment(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isCancelable();
    }

    private FragmentDialogWarningBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDialogWarningBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.dialogTv1.setText(title);
        binding.dialogTv2.setText(content);

        binding.dialogTv3.setOnClickListener(v -> {
            dismiss();
        });

    }
}