package com.example.village.home.searchresult;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.village.R;
import com.example.village.home.HomeAdapter;
import com.example.village.post.Post;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder> {

    Context mContext;
    SearchResultViewModel viewModel;

    public SearchResultAdapter(Context mContext) {
        this.mContext = mContext;
        viewModel = new ViewModelProvider((ViewModelStoreOwner) mContext).get(SearchResultViewModel.class);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.post_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.productTitleTv.setText(viewModel.getPostArrayList().get(position).title);
        holder.sellerLocationTv.setText(viewModel.getPostArrayList().get(position).location);
        holder.productPriceTv.setText(viewModel.getPostArrayList().get(position).price);
        holder.homeImageIv.setImageURI(viewModel.getPostArrayList().get(position).HomeImageuri);
        Glide.with(holder.itemView)
                .load(viewModel.getPostArrayList().get(position).HomeImageuri)
                .into(holder.homeImageIv);
        holder.wholeLayout.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, Post.class);
            intent.putExtra("postNumber",viewModel.getPostArrayList().get(position).postNum);
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return viewModel.postArrayList != null ? viewModel.postArrayList.size() : 0;
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout wholeLayout;
        ImageView homeImageIv;
        TextView productTitleTv;
        TextView sellerLocationTv;
        TextView productPriceTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            wholeLayout = itemView.findViewById(R.id.wholeLayout);
            homeImageIv = itemView.findViewById(R.id.homeImageIv);
            productTitleTv = itemView.findViewById(R.id.productTitleTv);
            sellerLocationTv = itemView.findViewById(R.id.sellerLocationTv);
            productPriceTv = itemView.findViewById(R.id.productPriceTv);
        }
    }
}
