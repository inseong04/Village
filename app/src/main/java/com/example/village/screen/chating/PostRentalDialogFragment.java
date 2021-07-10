package com.example.village.screen.chating;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.village.R;
import com.example.village.databinding.FragmentPostRentalDialogBinding;
import com.example.village.screen.chating.Chating;
import com.example.village.util.SendNotification;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class PostRentalDialogFragment extends DialogFragment {

    private Context context;
    private String productName;
    private String postNumber;
    private FragmentPostRentalDialogBinding binding;
    private Chating chating;

    public PostRentalDialogFragment(Context context, Chating chating, String productName, String postNumber) {
        this.context = context;
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
                                        if(documentSnapshot.get("rentalProduct").equals("")) {
                                            rentalProduct = postNumber;
                                        } else {
                                            rentalProduct = ((String) documentSnapshot.get("rentalProduct")) + "-" + postNumber;
                                        }
                                    }
                                    else {
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
                                    ERROR : Fragment PostRentalDialogFragment{a537e4f} (9760d9b9-4c5c-4194-87e4-f9921f85152d) not attached to a context.
                                    Dialog dialog = new com.example.village.util.Dialog(context,getResources().getDisplayMetrics(), "채팅하기", "자신의 상품은 \n대여가 불가능합니다.");
                                    dialog.getWindow().setGravity(Gravity.CENTER);
                                    dialog.show();
                                });
                    });
        });

        binding.btn2.setOnClickListener(v -> {
            dismiss();
        });

    }
}