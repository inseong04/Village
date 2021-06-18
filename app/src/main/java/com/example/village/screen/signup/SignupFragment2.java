package com.example.village.screen.signup;

import android.graphics.Color;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.village.R;
import com.example.village.databinding.FragmentSignup2Binding;
import com.example.village.util.Format;

import java.util.regex.Pattern;


public class SignupFragment2 extends Fragment {

    FragmentSignup2Binding binding;
    SignupViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_signup2,
                container, false);
        binding.setLifecycleOwner(getActivity());
        viewModel = new ViewModelProvider(getActivity()).get(SignupViewModel.class);
        binding.setViewModel(viewModel);

        viewModel.getPhoneNumber().observe(getActivity(), text -> {
            Log.e("t1est","text"+text);
            if(isVaildPhoneNumber(text)) {
                binding.btnPhoneNext.setEnabled(true);
                binding.alarm2.setImageResource(R.drawable.ic_signup_active);
                binding.btnPhoneNext.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.btn_active));
            } else {
                binding.btnPhoneNext.setEnabled(false);
                binding.alarm2.setImageResource(R.drawable.ic_signup_inactivation);
                binding.btnPhoneNext.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.btn_in_active));
            }
        });

        binding.btnPhoneNext.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment,
                    new SignupFragment3())
                    .commit();
            viewModel.setPhoneNumber(Format.phoneNumberFormat(viewModel.getPhoneNumber().getValue()));
        });

        return binding.getRoot();
    }

    private boolean isVaildPhoneNumber(String phoneNumber) {
        Pattern pattern = Pattern.compile("(\\d{3})(\\d{3,4})(\\d{4})");
        return pattern.matcher(phoneNumber).matches();
    }
}