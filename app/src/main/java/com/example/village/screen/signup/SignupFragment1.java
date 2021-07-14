package com.example.village.screen.signup;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.village.R;
import com.example.village.databinding.FragmentSignup1Binding;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class SignupFragment1 extends Fragment {

    private SignupViewModel viewModel;
    private ArrayList<String> nameList;
    private FragmentSignup1Binding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_signup1,
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
                                nameList = (ArrayList<String>) task.getResult().get("nameList");
                            });
                });
        thread.start();


        viewModel.getName().observe(getActivity(), text -> {
            if (!text.equals("")) {

                if (nameList != null) {
                    for (int i=0; i< nameList.size(); i++) {
                        if (text.equals(nameList.get(i))) {
                            setInActive();
                            binding.alarm5.setVisibility(View.VISIBLE);
                            break;
                        }
                        if (!text.equals(nameList.get(i))) {
                            if (binding.alarm5.getVisibility() == View.VISIBLE)
                                binding.alarm5.setVisibility(View.INVISIBLE);
                            setActive();
                        }
                    }

                }
            } else {
                setInActive();
            }
        });

        binding.btnNameNext.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment,
                    new SignupFragment2())
                    .commit();
        });

        binding.etvName.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                return keyCode == KeyEvent.KEYCODE_ENTER;
            }
        });

        return binding.getRoot();
    }

    private void setActive () {
        binding.btnNameNext.setEnabled(true);
        binding.alarm1.setImageResource(R.drawable.ic_signup_active);
        binding.btnNameNext.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.btn_active));
    }
    private void setInActive () {
        binding.btnNameNext.setEnabled(false);
        binding.alarm1.setImageResource(R.drawable.ic_signup_inactivation);
        binding.btnNameNext.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.btn_in_active));
    }

}