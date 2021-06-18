package com.example.village.screen.signup;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.village.R;
import com.example.village.databinding.FragmentSignup5Binding;
import com.example.village.databinding.FragmentSignup6Binding;
import com.example.village.screen.login.Login;

public class SignupFragment6 extends Fragment {

    private FragmentSignup6Binding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_signup6,
                container, false);

        binding.signupBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Login.class);
            startActivity(intent);
            getActivity().finish();
        });

        return binding.getRoot();
    }
}