package com.example.village;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RentalAdapter extends RecyclerView.Adapter<Holder> {
    ArrayList<String> list;

    RentalAdapter(ArrayList<String> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.row_rental_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.productTitleTv.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class Holder extends RecyclerView.ViewHolder {
    ImageView rentalImageIv;
    TextView productTitleTv;
    TextView sellerLocationTv;
    TextView productPriceTv;

    public Holder(@NonNull View itemView) {
        super(itemView);
        rentalImageIv = itemView.findViewById(R.id.rentalImageIv);
        productTitleTv = itemView.findViewById(R.id.productTitleTv);
        sellerLocationTv = itemView.findViewById(R.id.sellerLocationTv);
        productPriceTv = itemView.findViewById(R.id.productPriceTv);
    }
}
