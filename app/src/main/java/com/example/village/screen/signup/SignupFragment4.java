package com.example.village.screen.signup;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.village.R;
import com.example.village.databinding.FragmentSignup3Binding;
import com.example.village.databinding.FragmentSignup4Binding;
import com.example.village.screen.login.Login;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class SignupFragment4 extends Fragment {

    FragmentSignup4Binding binding;
    SignupViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_signup4,
                container, false);
        binding.setLifecycleOwner(getActivity());
        viewModel = new ViewModelProvider(getActivity()).get(SignupViewModel.class);
        binding.setViewModel(viewModel);

        viewModel.getEmail().observe(getActivity(), text -> {
            if (Patterns.EMAIL_ADDRESS.matcher(text).matches()) {
                binding.alarm3.setImageResource(R.drawable.ic_signup_active);
                binding.btnIdNext.setEnabled(true);
                binding.btnIdNext.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.btn_active));;
            } else {
                binding.btnIdNext.setEnabled(false);
                binding.alarm3.setImageResource(R.drawable.ic_signup_inactivation);
                binding.btnIdNext.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.btn_in_active));
            }
        });

        binding.btnIdNext.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment, new SignupFragment5())
                    .commit();
        });

        return binding.getRoot();
    }

}

