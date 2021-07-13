package com.example.village.screen.my.rentalproduct;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.village.R;

import org.jetbrains.annotations.NotNull;

public class RentalAdapter extends RecyclerView.Adapter<RentalAdapter.ViewHolder> {

    private RentalProductViewModel viewModel;

    RentalAdapter(RentalProductViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.rental_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.rentalTv1.setText(viewModel.rentalArrayList.get(position).title);
        holder.rentalTv2.setText(viewModel.rentalArrayList.get(position).location);
        holder.rentalTv3.setText(viewModel.rentalArrayList.get(position).price);

        Glide.with(holder.itemView)
                .load(viewModel.rentalArrayList.get(position).productUri)
                .into(holder.rentalIv);
    }

    @Override
    public int getItemCount() {
        return viewModel.rentalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView rentalIv;
        TextView rentalTv1;
        TextView rentalTv2;
        TextView rentalTv3;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            rentalIv = itemView.findViewById(R.id.rentalIv);
            rentalTv1 = itemView.findViewById(R.id.rentalTv1);
            rentalTv2 = itemView.findViewById(R.id.rentalTv2);
            rentalTv3 = itemView.findViewById(R.id.rentalTv3);
        }
    }
}
