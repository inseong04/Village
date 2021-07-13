package com.example.village.screen.signup;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.village.R;
import com.example.village.databinding.FragmentSignup5Binding;
import com.example.village.util.Expression;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SignupFragment5 extends Fragment {
    FragmentSignup5Binding binding;
    SignupViewModel viewModel;
    FirebaseAuth mAuth;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_signup5,
                container, false);

        binding.setLifecycleOwner(getActivity());
        viewModel = new ViewModelProvider(getActivity()).get(SignupViewModel.class);
        binding.setViewModel(viewModel);
        mAuth = FirebaseAuth.getInstance();


        viewModel.getPassword().observe(getActivity(), text -> {
            if (Expression.isVaildPw(text)) {
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
                                    Dialog dialog = new com.example.village.util.Dialog(getContext(),getResources().getDisplayMetrics(), "회원가입", "회원가입 중 오류가 발생했습니다");
                                    dialog.getWindow().setGravity(Gravity.CENTER);
                                    dialog.show();
                                }
                            });
        });

        binding.etvPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                return keyCode == KeyEvent.KEYCODE_ENTER;
            }
        });

        return binding.getRoot();
    }

    protected void saveUserInf (String name, String location, String phoneNumber) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        Thread thread = new Thread(
                () -> {

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

                });
        thread.start();
        Thread thread1 = new Thread(
                () -> {
                    FirebaseFirestore.getInstance().collection("users")
                            .document("Storage")
                            .get()
                            .addOnCompleteListener(task -> {
                                ArrayList<String> emailList = (ArrayList<String>) task.getResult().get("emailList");
                                ArrayList<String> phoneList = (ArrayList<String>) task.getResult().get("phoneList");
                                ArrayList<String> nameList = (ArrayList<String>) task.getResult().get("nameList");

                                if (emailList != null && phoneList != null && nameList != null) {
                                    emailList.add(viewModel.getEmail().getValue());
                                    phoneList.add(viewModel.getPhoneNumber().getValue().replace("-",""));
                                    nameList.add(viewModel.getName().getValue());
                                }
                                Map<String, Object> map = new HashMap<>();
                                map.put("emailList", emailList);
                                map.put("phoneList", phoneList);
                                map.put("nameList", nameList);
                                FirebaseFirestore.getInstance().collection("users")
                                        .document("Storage")
                                        .update(map);
                            });
                });
        thread1.start();
    }
}