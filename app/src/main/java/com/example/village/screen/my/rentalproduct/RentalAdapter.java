package com.example.village.screen.my.rentalproduct;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.village.R;
import com.example.village.screen.post.Post;

import org.jetbrains.annotations.NotNull;

public class RentalAdapter extends RecyclerView.Adapter<RentalAdapter.ViewHolder> {

    private final RentalProductViewModel viewModel;
    private final Context context;

    RentalAdapter(RentalProductViewModel viewModel, Context context) {
        this.viewModel = viewModel;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.post_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.productTitleTv.setText(viewModel.rentalArrayList.get(position).title);
        holder.sellerLocationTv.setText(viewModel.rentalArrayList.get(position).location);
        holder.productPriceTv.setText(viewModel.rentalArrayList.get(position).price);

        if (viewModel.rentalArrayList.get(position).rental) {
            holder.rentalTv.setText("대여중");
            holder.rentalTv.setTextColor(Color.parseColor("#000000"));
            holder.rentalTv.setBackground(ContextCompat.getDrawable(context, R.drawable.rental_true));
        } else {
            holder.rentalTv.setText("대여가능");
            holder.rentalTv.setTextColor(Color.parseColor("#ffffff"));
            holder.rentalTv.setBackground(ContextCompat.getDrawable(context, R.drawable.rental_false));
        }

        holder.wholeLayout.setOnClickListener(v -> {
            Intent intent = new Intent(context, Post.class);
            intent.putExtra("postNumber", viewModel.rentalArrayList.get(position).postNum);
            intent.putExtra("chatIntent", false);
            context.startActivity(intent);
        });



        Glide.with(holder.itemView)
                .load(viewModel.rentalArrayList.get(position).productUri)
                .into(holder.homeImageIv);
    }

    @Override
    public int getItemCount() {
        return viewModel.rentalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout wholeLayout;
        ImageView homeImageIv;
        TextView productTitleTv;
        TextView sellerLocationTv;
        TextView productPriceTv;
        TextView rentalTv;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            wholeLayout = itemView.findViewById(R.id.wholeLayout);
            homeImageIv = itemView.findViewById(R.id.homeImageIv);
            productTitleTv = itemView.findViewById(R.id.productTitleTv);
            sellerLocationTv = itemView.findViewById(R.id.sellerLocationTv);
            productPriceTv = itemView.findViewById(R.id.productPriceTv);
            rentalTv = itemView.findViewById(R.id.rentalTv);
        }
    }
}
