package com.example.village.screen.productwriting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.village.R;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    Context context;
    ProductViewModel viewModel;

    public ProductAdapter(Context context) {
        this.context = context;
        this.viewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(ProductViewModel.class);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.product_image_item,parent,false);

        return new ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductData productData = viewModel.arrayList.get(position);
        holder.ItemIv.setImageURI(productData.uri);

        holder.deleteItem.setOnClickListener(v -> {
            viewModel.arrayList.remove(position);
            notifyItemRemoved(position);
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        try {
            return viewModel.arrayList.size();
        } catch(NullPointerException e) {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ItemIv;
        Button deleteItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ItemIv = itemView.findViewById(R.id.itemIv);
            deleteItem = itemView.findViewById(R.id.deleteItem);
        }
    }
}
