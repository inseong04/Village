package com.example.village.screen.my;

import android.content.Context;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.village.R;
import com.example.village.databinding.FragmentLocationBinding;
import com.example.village.screen.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class LocationFragment extends Fragment {
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

    private FragmentLocationBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_location, container, false);

        FirebaseUser curuser = auth.getCurrentUser();
        String useruid = auth.getUid();

        binding.changebtn.setOnClickListener(view -> {
            db.collection("users").document(useruid).update("location", binding.editText.getText().toString());
            binding.editText.setText("");
            activity.onFragmentChange(1);

            UpdateLocation updateLocation = new UpdateLocation(binding.editText.getText().toString());
            updateLocation.start();
        });

        return binding.getRoot();
    }
}