package com.example.village.screen.chating;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.village.R;
import com.example.village.util.DataType;

public class ChatingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ChatingViewModel viewModel;

    ChatingAdapter (ChatingViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view;
        if (viewType == DataType.RECEIVE_MESSAGE) {
            view = layoutInflater.inflate(R.layout.receive_chat_item, parent, false);
            return new ReceiveViewHolder(view);
        }
        else {
            view = layoutInflater.inflate(R.layout.send_chat_item, parent, false);
            return new SendViewHolder(view);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ReceiveViewHolder) {
            ((ReceiveViewHolder) viewHolder).receiveTextTv.setText(viewModel.getChatArrayList().get(position).getContent());

            if (position > 0 &&
                    viewModel.getChatArrayList().get(position).getUid().equals(viewModel.getChatArrayList().get(position-1).getUid()))
                ((ReceiveViewHolder)viewHolder).chat_iv.setVisibility(View.INVISIBLE);
        }
        else
            ((SendViewHolder) viewHolder).sendTextTv.setText(viewModel.getChatArrayList().get(position).getContent());

    }

    @Override
    public int getItemCount() {
        return viewModel.getChatArrayList().size();
    }

    public int getItemViewType(int position) {
        return viewModel.getChatArrayList().get(position).getUid().equals(viewModel.getUid())
                ? DataType.SEND_MESSAGE : DataType.RECEIVE_MESSAGE;
    }

    public class ReceiveViewHolder extends  RecyclerView.ViewHolder {

        ImageView chat_iv;
        TextView receiveTextTv;
        public ReceiveViewHolder(@NonNull View itemView) {
            super(itemView);
            chat_iv = itemView.findViewById(R.id.chatIv);
            receiveTextTv = itemView.findViewById(R.id.receiveTextTv);
        }
    }

    public class SendViewHolder extends RecyclerView.ViewHolder {

        TextView sendTextTv;
        public SendViewHolder(@NonNull View itemView) {
            super(itemView);
            sendTextTv = itemView.findViewById(R.id.sendTextTv);
        }
    }
}
