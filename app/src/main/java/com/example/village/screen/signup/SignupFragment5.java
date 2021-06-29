package com.example.village.screen.signup;

import android.content.Intent;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.village.R;
import com.example.village.databinding.FragmentSignup4Binding;
import com.example.village.databinding.FragmentSignup5Binding;
import com.example.village.screen.login.Login;
import com.example.village.util.WarningDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class SignupFragment5 extends Fragment {
    FragmentSignup5Binding binding;
    SignupViewModel viewModel;
    FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_signup5,
                container, false);

        binding.setLifecycleOwner(getActivity());
        viewModel = new ViewModelProvider(getActivity()).get(SignupViewModel.class);
        binding.setViewModel(viewModel);
        mAuth = FirebaseAuth.getInstance();

        viewModel.getPassword().observe(getActivity(), text -> {
            if (isVaildPw(text)) {
                binding.alarm4.setImageResource(R.drawable.ic_signup_active);
                binding.btnPasswordNext.setEnabled(true);
                binding.btnPasswordNext.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.btn_active));
            } else {
                binding.alarm4.setImageResource(R.drawable.ic_signup_inactivation);
                binding.btnPasswordNext.setEnabled(false);
                binding.btnPasswordNext.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.btn_in_active));
            }
        });

        binding.btnPasswordNext.setOnClickListener(v -> {
            mAuth.createUserWithEmailAndPassword(viewModel.getEmail().getValue(), viewModel.getPassword().getValue())
                    .addOnCompleteListener(getActivity(),
                            task -> {
                                if (task.isSuccessful()) {
                                    saveUserInf(viewModel.getName().getValue(),
                                            viewModel.getLocation().getValue(),
                                            viewModel.getPhoneNumber().getValue()
                                            );
                                    getActivity().getSupportFragmentManager().beginTransaction()
                                            .replace(R.id.fragment, new SignupFragment6())
                                            .commit();
                                }
                                else {
                                    WarningDialogFragment warningDialogFragment = new WarningDialogFragment("회원가입", "회원가입 중 오류가 발생했습니다 \n 앱을 종료합니다.");
                                    warningDialogFragment.show(getActivity().getSupportFragmentManager(), "dialogFragment");
                                }
                            });
        });
        return binding.getRoot();
    }

    private boolean isVaildPw(String pw) {
        Pattern pattern = Pattern.compile("(^.*(?=.{6,24})(?=.*[0-9])(?=.*[A-z]).*$)");
        return pattern.matcher(pw).matches();
    }

    protected void saveUserInf (String name, String location, String phoneNumber) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(task -> {
                    String token = task.getResult().getToken();
                    Map<String, Object> map = new HashMap<>();
                    map.put("fcmToken", token);
                    map.put("name", name);
                    map.put("location", location);
                    map.put("phoneNumber", phoneNumber);

                    db.collection("users")
                            .document(uid)
                            .set(map);
                });
    }
}