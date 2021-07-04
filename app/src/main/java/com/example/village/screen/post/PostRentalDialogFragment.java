package com.example.village.screen.post;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.village.R;
import com.example.village.databinding.FragmentPostRentalDialogBinding;
import com.example.village.screen.chating.Chating;
import com.example.village.util.SendNotification;
import com.example.village.util.WarningDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class PostRentalDialogFragment extends DialogFragment {

    private String productName;
    private String postNumber;
    private FragmentPostRentalDialogBinding binding;
    private Chating chating;

    public PostRentalDialogFragment(Chating chating, String productName, String postNumber) {
        this.chating = chating;
        this.productName = productName;
        this.postNumber = postNumber;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isCancelable();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_post_rental_dialog,
                container, false);
        binding.setContent(productName+" 을(를)\n대여하시겠습니까?");

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btn1.setOnClickListener(v -> {
            final FirebaseFirestore db = FirebaseFirestore.getInstance();
            Map<String, Object> map = new HashMap<>();
            map.put("rental", true);

            Thread thread = new Thread(
                    () -> {
                        db.collection("users").document(FirebaseAuth.getInstance().getUid())
                                .get()
                                .addOnCompleteListener(usersGetTask -> {
                                    final DocumentSnapshot documentSnapshot = usersGetTask.getResult();
                                    String rentalProduct;
                                    if (documentSnapshot.get("rentalProduct") != null ) {
                                        rentalProduct = ((String) documentSnapshot.get("rentalProduct"))+"-"+postNumber;
                                    } else {
                                        rentalProduct = postNumber;
                                    }

                                    db.collection("users").document(FirebaseAuth.getInstance().getUid())
                                            .update("rentalProduct", rentalProduct);
                                });
                    });
            thread.start();

            db.collection("post")
                    .document(postNumber)
                    .update(map)
                    .addOnCompleteListener(task -> {
                        dismiss();
                        FirebaseFirestore.getInstance().collection("post")
                                .document(postNumber)
                                .get()
                                .addOnCompleteListener(dbTask -> {
                                    SendNotification.sendNotification(String.valueOf(dbTask.getResult().get("fcmToken")),
                                            "대여신청이 도착했습니다.",
                                            productName+" 게시물의 대여신청이 도착했습니다.");
                                    WarningDialogFragment warningDialogFragment = new WarningDialogFragment("대여하기", "대여가 완료되었습니다.");
                                    warningDialogFragment.show(chating.getSupportFragmentManager(), "dialogFragment");
                                });
                    });
        });

        binding.btn2.setOnClickListener(v -> {
            dismiss();
        });

    }
}