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
import com.example.village.util.Expression;
import com.example.village.util.Format;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.regex.Pattern;


public class SignupFragment2 extends Fragment {

    FragmentSignup2Binding binding;
    SignupViewModel viewModel;
    ArrayList<String> phoneList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_signup2,
                container, false);
        binding.setLifecycleOwner(getActivity());
        viewModel = new ViewModelProvider(getActivity()).get(SignupViewModel.class);
        binding.setViewModel(viewModel);

        Thread thread = new Thread(
                () -> {
                    FirebaseFirestore.getInstance().collection("users")
                            .document("Storage")
                            .get()
                            .addOnCompleteListener(task -> {
                                phoneList = (ArrayList<String>) task.getResult().get("phoneList");
                            });
                });
        thread.start();

        viewModel.getPhoneNumber().observe(getActivity(), text -> {
            if (!text.equals("")) {

                if (Expression.isVaildPhoneNumber(text)) {

                    if (phoneList != null) {
                        for (int i = 0; i < phoneList.size(); i++) {
                            if (text.equals(phoneList.get(i))) {
                                setInActive();
                                binding.alarm6.setVisibility(View.VISIBLE);
                                break;
                            }
                            if (!text.equals(phoneList.get(i))) {
                                if (binding.alarm6.getVisibility() == View.VISIBLE)
                                    binding.alarm6.setVisibility(View.INVISIBLE);
                                setActive();
                            }
                        }

                    }
                }
            }else {
                setInActive();
            }
        });

        binding.btnPhoneNext.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment,
                    new SignupFragment3())
                    .commit();
            viewModel.setPhoneNumber(Format.phoneNumberFormat(viewModel.getPhoneNumber().getValue()));
        });

        binding.etvPhone.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                return keyCode == KeyEvent.KEYCODE_ENTER;
            }
        });

        return binding.getRoot();
    }

    private void setActive () {
        binding.btnPhoneNext.setEnabled(true);
        binding.alarm2.setImageResource(R.drawable.ic_signup_active);
        binding.btnPhoneNext.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.btn_active));
    }

    private void setInActive () {
        binding.btnPhoneNext.setEnabled(false);
        binding.alarm2.setImageResource(R.drawable.ic_signup_inactivation);
        binding.btnPhoneNext.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.btn_in_active));
    }
}