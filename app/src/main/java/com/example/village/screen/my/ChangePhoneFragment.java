package com.example.village.screen.my;

import android.content.Context;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.village.R;
import com.example.village.databinding.FragmentChangePhoneBinding;
import com.example.village.screen.MainActivity;
import com.example.village.util.Format;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.regex.Pattern;


public class ChangePhoneFragment extends Fragment {
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    ChangeprofileFragment changeprofileFragment = new ChangeprofileFragment();
    MainActivity activity;
    ArrayList<String> phoneList;

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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

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

        FirebaseUser curuser = auth.getCurrentUser();
        String useruid = auth.getUid();

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_change_phone,
                container, false);

        binding.changebtn.setOnClickListener(view -> {
            String phonenum = Format.phoneNumberFormat(binding.editText.getText().toString());

            if (phoneList != null) {
                for (int i = 0; i < phoneList.size(); i++) {
                    if (phonenum.equals(phoneList.get(i))) {
                        Toast.makeText(activity, "전화번호가 중복되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                    if (!phonenum.equals(phoneList.get(i))) {
                        db.collection("users").document(useruid).update("phoneNumber", phonenum);
                        binding.editText.setText("");
                        activity.onFragmentChange(1);
                    }
                }
            }
        });

        return binding.getRoot();
    }

    private boolean isVaildPhoneNumber(String phoneNumber) {
        Pattern pattern = Pattern.compile("(\\d{3})(\\d{3,4})(\\d{4})");
        return pattern.matcher(phoneNumber).matches();
    }
}