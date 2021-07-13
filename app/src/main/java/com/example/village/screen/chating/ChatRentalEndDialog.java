package com.example.village.screen.chating;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.example.village.R;
import com.example.village.databinding.ActivityChatingBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ChatRentalEndDialog extends Dialog {
    private Context context;
    private String postNumber;
    private DisplayMetrics displayMetrics;
    private String[] rentalProduct;
    private ActivityChatingBinding binding;
    private ChatingViewModel viewModel;
    ChatRentalEndDialog (Context context, DisplayMetrics displayMetrics,
                         String postNumber, String[] rentalProduct, ActivityChatingBinding binding, ChatingViewModel viewModel) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.context = context;
        this.displayMetrics = displayMetrics;
        this.postNumber = postNumber;
        this.rentalProduct = rentalProduct;
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
        setContentView(R.layout.chat_rentalend_dialog);

        findViewById(R.id.rentalEndBtn1).setOnClickListener(v -> {
            Map<String, Object> map = new HashMap<>();
            map.put("rental", false);
            FirebaseFirestore.getInstance().collection("post")
                    .document(postNumber)
                    .update(map)
                    .addOnCompleteListener(task -> {
                        StringBuilder rentalProduct2 = new StringBuilder();
                        for (int i=0; i<rentalProduct.length; i++) {
                            if (rentalProduct[i].equals(postNumber))
                                continue;
                            rentalProduct2.append(rentalProduct[i]);
                        }
                        Thread thread = new Thread(
                                () -> {
                                    Map<String, Object> map1 = new HashMap<>();
                                    map1.put("rentalProduct", rentalProduct2.toString());
                                   FirebaseFirestore.getInstance().collection("users")
                                   .document(FirebaseAuth.getInstance().getUid())
                                   .update(map1);
                                });
                        thread.start();
                        Dialog dialog = new com.example.village.util.Dialog(context,displayMetrics, "대여하기", "대여가 종료되었습니다.");
                        dialog.getWindow().setGravity(Gravity.CENTER);
                        dialog.show();
                        sendMessage("대여가 종료되었습니다.");
                        binding.rentalEndBtn.setVisibility(View.GONE);
                        binding.rentalBtn.setVisibility(View.VISIBLE);

                        dismiss();
                    });
        });

        findViewById(R.id.rentalEndBtn2).setOnClickListener(v -> {
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
