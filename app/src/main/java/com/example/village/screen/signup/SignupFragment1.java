package com.example.village.screen.signup;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.village.R;
import com.example.village.databinding.FragmentSignup1Binding;


public class SignupFragment1 extends Fragment {

    private SignupViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentSignup1Binding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_signup1,
                container, false);
        binding.setLifecycleOwner(getActivity());
        viewModel = new ViewModelProvider(getActivity()).get(SignupViewModel.class);
        binding.setViewModel(viewModel);

        viewModel.getName().observe(getActivity(), text -> {
            if (!text.equals("")) {
                binding.btnNameNext.setEnabled(true);
                binding.alarm1.setImageResource(R.drawable.ic_signup_active);
                binding.btnNameNext.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.btn_active));
            } else {
                binding.btnNameNext.setEnabled(false);
                binding.alarm1.setImageResource(R.drawable.ic_signup_inactivation);
                binding.btnNameNext.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.btn_in_active));
            }
        });

        binding.btnNameNext.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment,
                    new SignupFragment2())
                    .commit();
        });

        return binding.getRoot();
    }
}