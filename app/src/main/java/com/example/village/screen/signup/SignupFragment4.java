package com.example.village.screen.signup;

import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.village.R;
import com.example.village.databinding.FragmentSignup4Binding;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;


public class SignupFragment4 extends Fragment {

    FragmentSignup4Binding binding;
    SignupViewModel viewModel;
    ArrayList<String> emailList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_signup4,
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
                                emailList = (ArrayList<String>) task.getResult().get("emailList");
                            });
                });
        thread.start();


        viewModel.getEmail().observe(getActivity(), text -> {
            if (Patterns.EMAIL_ADDRESS.matcher(text).matches()) {
                    for (int i=0; i< emailList.size(); i++) {
                        if (text.equals(emailList.get(i))) {
                            setInActive();
                            binding.alarm7.setVisibility(View.VISIBLE);
                            break;
                        }
                        if (!text.equals(emailList.get(i))) {
                            if (binding.alarm7.getVisibility() == View.VISIBLE)
                                binding.alarm7.setVisibility(View.INVISIBLE);
                            setActive();
                        }
                    }
            } else {
                setInActive();
            }
        });

        binding.btnIdNext.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment, new SignupFragment5())
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

    private void setActive () {
        binding.alarm3.setImageResource(R.drawable.ic_signup_active);
        binding.btnIdNext.setEnabled(true);
        binding.btnIdNext.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.btn_active));;
    }

    private void setInActive () {
        binding.btnIdNext.setEnabled(false);
        binding.alarm3.setImageResource(R.drawable.ic_signup_inactivation);
        binding.btnIdNext.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.btn_in_active));
    }

}

