package com.example.village.screen.my;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.village.R;
import com.example.village.databinding.FragmentChangePhoneBinding;
import com.example.village.screen.MainActivity;
import com.example.village.screen.my.rentaledproduct.RentaledActivity;
import com.example.village.screen.my.rentaledproduct.RentaledViewModel;
import com.example.village.screen.my.rentalproduct.RentalProductActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private MainActivity activity;
    private Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) getActivity();
        this.context = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        activity = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private String profileUsername;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.fragment_profile, null);

        TextView txt_username = v.findViewById(R.id.text_profile_username);
        profileUsername = ((MainActivity)MainActivity.nContext).getProfileUsername();
        txt_username.setText(profileUsername);

        Button button_changeprofile = v.findViewById(R.id.button_profile_changeprofile);
        Button button_rental = v.findViewById(R.id.button_profile_local);
        Button button_rented = v.findViewById(R.id.button_profile_phone);
        Button rentalbtn = v.findViewById(R.id.rentalbtn);
        Button rentaledbtn = v.findViewById(R.id.rentaledbtn);
        button_changeprofile.setOnClickListener(this);
        button_rental.setOnClickListener(this);
        button_rented.setOnClickListener(this);
        rentalbtn.setOnClickListener(it -> {
            Intent intent = new Intent(activity.getApplicationContext(), RentalProductActivity.class);
            startActivity(intent);
        });

        rentaledbtn.setOnClickListener(it -> {
            Intent intent = new Intent(activity.getApplicationContext(), RentaledActivity.class);
            startActivity(intent);

        });

        return v;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.button_profile_changeprofile:
                activity.onFragmentChange(2);
                break;
            case R.id.button_profile_local:
                activity.onFragmentChange(3);
                break;
            case R.id.button_profile_phone:
                activity.onFragmentChange(4);
                break;
            case R.id.rentalbtn:
                activity.onFragmentChange(5);
                break;
            case R.id.rentaledbtn:
                //TODO
        }

    }


}