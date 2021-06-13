package com.example.village.home.search;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.village.R;

import java.util.ArrayList;
import java.util.Objects;

public class SearchAdapter extends RecyclerView.Adapter<Holder> {

    SearchViewModel viewModel;

    SearchAdapter(Context context) {
        this.viewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(SearchViewModel.class);
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.searchword_item,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Log.e("aa","position"+position+":  "+viewModel.searchWord.getValue().get(position));
        holder.text.setText(viewModel.searchWord.getValue().get(position));

        holder.delete_btn.setOnClickListener(v -> {
                removeItem(position);
        });

        holder.text.setOnClickListener(v -> {
            viewModel.setSearchEtvText(viewModel.searchWord.getValue().get(position));
        });

    }

    @Override
    public int getItemCount() {
        try {
            return viewModel.searchWord.getValue().size();
        } catch (NullPointerException e) {
            return 0;
        }
    }

    public void removeItem(int position) {
        if(position == 0 && viewModel.searchWord.getValue().size() == 0) {
            viewModel.searchWordDeleteIndex0.setValue(true);
            viewModel.emptyAlarm.setValue(true);
            viewModel.first = true;
        }
        viewModel.removeSearchWord(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();

    }

}

class Holder extends RecyclerView.ViewHolder {
    TextView text;
    ImageButton delete_btn;

    public Holder(@NonNull View itemView) {
        super(itemView);
        this.text = itemView.findViewById(R.id.text);
        this.delete_btn = itemView.findViewById(R.id.delete_btn);
    }

}

