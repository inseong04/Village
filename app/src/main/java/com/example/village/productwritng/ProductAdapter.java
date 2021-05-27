package com.example.village.productwritng;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.village.R;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    ArrayList<ProductData> arrayList;

    public ProductAdapter(ArrayList<ProductData> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.product_image_item,parent,false);

        return new ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductData productData = arrayList.get(position);
        holder.ItemIv.setImageURI(productData.uri);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
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
