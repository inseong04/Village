package com.example.village;

import android.content.Context;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.village.databinding.FragmentChangePhoneBinding;
import com.example.village.screen.MainActivity;
import com.example.village.util.Format;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.regex.Pattern;


public class ChangePhoneFragment extends Fragment {
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    MainActivity activity;

    private static final String TAG = "MainActivity";
    private Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        activity = (MainActivity) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }

    FragmentChangePhoneBinding binding;
    ChangePhoneNumberViewModel viewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FirebaseUser curuser = auth.getCurrentUser();
        String useruid = auth.getUid();

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_change_phone,
                container, false);
        viewModel = new ViewModelProvider(getActivity()).get(ChangePhoneNumberViewModel.class);

//        final String[] phone = new String[1];
//        viewModel.getPhoneNumber().observe(getActivity(), text -> {
//            if (isVaildPhoneNumber(text)) {
//                // edittext의 내용이 01012345678 형식일시
//                phone[0] = Format.phoneNumberFormat(viewModel.getPhoneNumber().getValue()); // 010-1234-5678 형식으로 변환
//
//            }
//            Log.i(TAG, text);
//        });
        binding.changebtn.setOnClickListener(view -> {
            String phonenum = Format.phoneNumberFormat(binding.editText.getText().toString());
            db.collection("users").document(useruid).update("phoneNumber", phonenum);
            binding.editText.setText("");
            activity.onFragmentChange(1);
        });

        return binding.getRoot();
    }

    private boolean isVaildPhoneNumber(String phoneNumber) {
        Pattern pattern = Pattern.compile("(\\d{3})(\\d{3,4})(\\d{4})");
        return pattern.matcher(phoneNumber).matches();
    }
}