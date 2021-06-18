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
import com.google.firebase.firestore.FirebaseFirestore;

public class ChangeprofileFragment extends Fragment {
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

    private FragmentChangeprofileBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_changeprofile, container, false);

        FirebaseUser curuser = auth.getCurrentUser();
        String useruid = auth.getUid();

        db.collection("users").document(user.getUid()).get()
                .addOnCompleteListener(it->{
                    String username = it.getResult().get("name").toString();
                    binding.editText.setHint(username);
                });

        binding.changebtn.setOnClickListener(view -> {
            db.collection("users").document(useruid).update("name", binding.editText.getText().toString());
            binding.editText.setText("");
            activity.onFragmentChange(1);
        });

        return binding.getRoot();
    }
}