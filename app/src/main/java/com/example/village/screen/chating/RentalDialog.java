package com.example.village.screen.chating;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.village.R;
import com.example.village.databinding.ActivityChatingBinding;
import com.example.village.util.SendNotification;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class RentalDialog extends Dialog {

    private Context context;
    private DisplayMetrics displayMetrics;
    private String postNumber;
    private String productName;
    private ActivityChatingBinding binding;
    private ChatingViewModel viewModel;

    public RentalDialog(@NonNull Context context, DisplayMetrics displayMetrics, String postNumber, String productName,
                        ActivityChatingBinding binding, ChatingViewModel viewModel) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.context = context;
        this.displayMetrics = displayMetrics;
        this.postNumber = postNumber;
        this.productName = productName;
        this.binding = binding;
        this.viewModel = viewModel;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        params.dimAmount = 0.5f;
        params.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 254, displayMetrics);
        params.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 144, displayMetrics);
        getWindow().setAttributes(params);
        setContentView(R.layout.rental_dialog);

        ((TextView)findViewById(R.id.rentalDialogTv2)).setText(productName+"을(를)\n대여하시겠습니까?");

        findViewById(R.id.rentalDialogBtn1).setOnClickListener(v -> {
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
                                    Dialog dialog = new com.example.village.util.Dialog(context, displayMetrics, "채팅하기", "대여가 완료되었습니다.");
                                    dialog.getWindow().setGravity(Gravity.CENTER);
                                    dialog.show();
                                    sendMessage("대여신청이 도착했습니다!\n지금 바로 협의 후\n거래를 진행해보세요!");
                                    binding.rentalEndBtn.setVisibility(View.VISIBLE);
                                    binding.rentalBtn.setVisibility(View.GONE);
                                });
                    });
        });

        findViewById(R.id.rentalDialogBtn2).setOnClickListener(v -> {
            dismiss();
        });
    }

    private void sendMessage(String text1) {
            long lastMessageTime = System.currentTimeMillis();
            String text = text1;
            viewModel.addChatArrayList(viewModel.getUid(), text);
            viewModel.setChatSum(viewModel.getChatSum() + 1);
            Map<String, Object> map = new HashMap<>();
            map.put("lastMessageTime", lastMessageTime);
            map.put(viewModel.getUid(), text);
            map.put("lastMessage", text);
            map.put("chat-" + viewModel.getChatSum(), text);
            map.put("chat-uid-" + viewModel.getChatSum(), viewModel.getUid());
            map.put("chatSum", viewModel.getChatSum());
            SendAsyncTask sendAsyncTask = new SendAsyncTask(viewModel.sellerUid, viewModel.getUid(), viewModel.roomNumber, map);
            sendAsyncTask.execute();
            binding.chatEtv.setText("");
            binding.chatRecyclerView.getAdapter().notifyDataSetChanged();
            binding.chatRecyclerView.smoothScrollToPosition(viewModel.getChatArrayList().size() - 1);
            Thread thread = new Thread(
                    () -> {
                        Map<String, Object> map2 = new HashMap<>();
                        map.put(viewModel.getUid()+"-chatCount", viewModel.getChatSum());
                        FirebaseFirestore.getInstance().collection("chat")
                                .document(viewModel.getRoomNumber())
                                .update(map2);
                    });
            thread.start();
    }
}