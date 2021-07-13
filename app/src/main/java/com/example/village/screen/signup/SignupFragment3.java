package com.example.village.screen.signup;

import android.graphics.Color;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.village.R;
import com.example.village.databinding.FragmentSignup2Binding;
import com.example.village.databinding.FragmentSignup3Binding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;


public class SignupFragment3 extends Fragment {

    FragmentSignup3Binding binding;
    SignupViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_signup3,
                container, false);
        binding.setLifecycleOwner(getActivity());
        viewModel = new ViewModelProvider(getActivity()).get(SignupViewModel.class);
        binding.setViewModel(viewModel);

        viewModel.getLocation().observe(getActivity(), text -> {
            if (!text.equals("")) {
                binding.btnLocationNext.setEnabled(true);
                binding.alarm2.setImageResource(R.drawable.ic_signup_active);
                binding.btnLocationNext.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.btn_active));
            } else {
                binding.btnLocationNext.setEnabled(false);
                binding.alarm2.setImageResource(R.drawable.ic_signup_inactivation);
                binding.btnLocationNext.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.btn_in_active));
            }
        });

        binding.btnLocationNext.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment,
                    new SignupFragment4())
                    .commit();
        });

        binding.etvId.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                return keyCode == KeyEvent.KEYCODE_ENTER;
            }
        });

        return binding.getRoot();
    }



}
