package com.example.village;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.village.screen.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    MainActivity activity;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) getActivity();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.fragment_profile, null);

        TextView txt_username = v.findViewById(R.id.text_profile_username);
        db.collection("users").document(user.getUid()).get()
                .addOnCompleteListener(it->{
                    String username = it.getResult().get("name").toString();
                    txt_username.setText(username);
                });

        Button button_changeprofile = v.findViewById(R.id.button_profile_changeprofile);//xml 잘못 작성해서 id 이상하게 설정됨...
        Button button_rental = v.findViewById(R.id.button_profile_local);  //대여등록한상품
        Button button_rented = v.findViewById(R.id.button_profile_password); //대여중인상품
        Button button_review = v.findViewById(R.id.button_profile_logout); //받은거래후기
        Button button_reviewed = v.findViewById(R.id.button_profile_end); //내가 쓴 거래후기

        button_changeprofile.setOnClickListener(this);
        button_rental.setOnClickListener(this);
        button_rented.setOnClickListener(this);
        button_review.setOnClickListener(this);
        button_reviewed.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.button_profile_changeprofile:
                activity.onFragmentChange(2);
                break;
            case R.id.button_profile_local:
                break;
            case R.id.button_profile_password:
                break;
            case R.id.button_profile_logout:
                break;
            case R.id.button_profile_end:
                break;
        }

    }
}