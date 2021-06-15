package com.example.village.screen.post;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.village.R;

public class PostPagerAdapter extends RecyclerView.Adapter<PostPagerAdapter.PagerViewHolder> {

    PostViewModel viewModel;

    public PostPagerAdapter(PostViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public PagerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_viewpager_image, parent, false);
        return new PagerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PagerViewHolder holder, int position) {
        Glide.with(holder.itemView)
                .load(viewModel.uriArrayList.get(position))
                .into(holder.viewpagerIv);
    }

    @Override
    public int getItemCount() {
        return viewModel.uriArrayList != null ? viewModel.uriArrayList.size() : 0;
    }

    public class PagerViewHolder extends RecyclerView.ViewHolder {
        ImageView viewpagerIv;

        public PagerViewHolder(@NonNull View itemView) {
            super(itemView);
            viewpagerIv = itemView.findViewById(R.id.viewpagerIv);

        }
    }


}


