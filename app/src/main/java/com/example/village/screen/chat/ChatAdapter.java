package com.example.village.screen.chat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.village.R;
import com.example.village.screen.chating.Chating;
import com.example.village.screen.home.HomeAdapter;
import com.google.firebase.firestore.FirebaseFirestore;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private ChatViewModel viewModel;
    private Context mContext;
    private Chat chat;

    public ChatAdapter(Chat chat, Context mContext, ChatViewModel viewModel) {
        this.chat = chat;
        this.mContext = mContext;
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.chat_room_item, parent, false);
        return new ChatAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.chatItemTv1.setText(viewModel.ChatListArrayList.get(position).userName);
        holder.chatDateTv.setText(viewModel.ChatListArrayList.get(position).date);
        holder.chatItemTv2.setText(viewModel.ChatListArrayList.get(position).lastMessage);
        if (viewModel.ChatListArrayList.get(position).unreadCount > 0) {
            holder.unreadCountTv.setVisibility(View.VISIBLE);
            holder.unreadCountTv.setText(String.valueOf(viewModel.ChatListArrayList.get(position).unreadCount));
        }
        if (viewModel.ChatListArrayList.get(position).unreadCount <= 0) {
            holder.unreadCountTv.setVisibility(View.GONE);
        }

        holder.chatRoomWholeLayout.setOnClickListener(v -> {

            FirebaseFirestore.getInstance().collection("chat")
                    .document(viewModel.ChatListArrayList.get(position).roomNumber)
                    .get()
                    .addOnCompleteListener(task -> {
                        String[] postNumber = viewModel.ChatListArrayList.get(position).roomNumber.split("-");
                        Intent intent = new Intent(mContext, Chating.class);
                        intent.putExtra("postNumber", postNumber[0]);
                        intent.putExtra("roomNumber", viewModel.ChatListArrayList.get(position).roomNumber);
                        intent.putExtra("sellerUid", String.valueOf(task.getResult().get("sellerUid")));
                        intent.putExtra("receiverName", viewModel.ChatListArrayList.get(position).userName);
                        chat.startActivityForResult(intent, 101);
                    });

        });
    }

    @Override
    public int getItemCount() {
        return viewModel.ChatListArrayList == null ? 0 : viewModel.ChatListArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout chatRoomWholeLayout;
        TextView chatItemTv1;
        TextView chatDateTv;
        TextView chatItemTv2;
        TextView unreadCountTv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            chatRoomWholeLayout = itemView.findViewById(R.id.chatRoomWholeLayout);
            chatItemTv1 = itemView.findViewById(R.id.chatItemTv1);
            chatDateTv = itemView.findViewById(R.id.chatDateTv);
            chatItemTv2 = itemView.findViewById(R.id.chatItemTv2);
            unreadCountTv = itemView.findViewById(R.id.unreadCountTv);
        }
    }
}
