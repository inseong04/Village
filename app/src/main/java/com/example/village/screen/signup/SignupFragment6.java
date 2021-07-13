package com.example.village.screen.signup;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.village.R;
import com.example.village.databinding.FragmentSignup6Binding;

public class SignupFragment6 extends Fragment {

    private FragmentSignup6Binding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_signup6,
                container, false);

        binding.signupBtn.setOnClickListener(v -> {
            getActivity().finish();
        });

        return binding.getRoot();
    }
}