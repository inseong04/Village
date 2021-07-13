package com.example.village.screen.my.rentaledproduct;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.village.R;
import com.example.village.screen.post.Post;

import org.jetbrains.annotations.NotNull;

public class RentaledAdapter extends RecyclerView.Adapter<RentaledAdapter.ViewHolder>{

    private final RentaledViewModel viewModel;
    private final Context context;

    RentaledAdapter(RentaledViewModel viewModel, Context context) {
        this.viewModel = viewModel;
        this.context = context;
    }

    @Override
    public RentaledAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.rental_item, parent, false);

        return new RentaledAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RentaledAdapter.ViewHolder holder, int position) {
        holder.rentalTv1.setText(viewModel.rentaledArrayList.get(position).title);
        holder.rentalTv2.setText(viewModel.rentaledArrayList.get(position).location);
        holder.rentalTv3.setText(viewModel.rentaledArrayList.get(position).price);

        Glide.with(holder.itemView)
                .load(viewModel.rentaledArrayList.get(position).productUri)
                .into(holder.rentalIv);

        holder.wholeLayout.setOnClickListener(v -> {
            Intent intent = new Intent(context, Post.class);
            intent.putExtra("postNumber", viewModel.rentaledArrayList.get(position).postNum);
            intent.putExtra("chatIntent", false);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return viewModel.rentaledArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout wholeLayout;
        ImageView rentalIv;
        TextView rentalTv1;
        TextView rentalTv2;
        TextView rentalTv3;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            wholeLayout = itemView.findViewById(R.id.wholeLayout);
            rentalIv = itemView.findViewById(R.id.rentalIv);
            rentalTv1 = itemView.findViewById(R.id.rentalTv1);
            rentalTv2 = itemView.findViewById(R.id.rentalTv2);
            rentalTv3 = itemView.findViewById(R.id.rentalTv3);
        }
    }
}
