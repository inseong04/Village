package com.example.village.screen.my;

import android.content.Context;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.village.R;
import com.example.village.databinding.FragmentChangeprofileBinding;
import com.example.village.screen.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChangeprofileFragment extends Fragment {
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    MainActivity activity;

    private static final String TAG = "MainActivity";

    private Context mContext;
    String username;
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

    private FragmentChangeprofileBinding binding;
    private String profileUsername;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_changeprofile, container, false);

        FirebaseUser curuser = auth.getCurrentUser();
        String useruid = auth.getUid();

        profileUsername = ((MainActivity)MainActivity.nContext).getProfileUsername();
        binding.editText.setHint(profileUsername);

        binding.changebtn.setOnClickListener(view -> {
            db.collection("users").document(useruid).update("name", binding.editText.getText().toString());
            ((MainActivity) MainActivity.nContext).changeName();

            db.collection("users").document(user.getUid()).get()
                    .addOnCompleteListener(it -> {
                        username = it.getResult().get("name").toString();
                        binding.editText.setHint(username);
                    });

            binding.changebtn.setOnClickListener(view1 -> {
                String newName = binding.editText.getText().toString();
                db.collection("users").document(useruid).update("name", newName);

                binding.editText.setText("");
                activity.onFragmentChange(1);

                UpdateDB updateDB = new UpdateDB(username, newName);
                updateDB.start();
            });
        });

        return binding.getRoot();
    }
}
