package com.example.village.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.village.R;
import com.example.village.databinding.FragmentHomeBinding;
import com.example.village.productwritng.ProductWritng;
import com.example.village.rdatabase.UserDatabase;


public class Home extends Fragment {


    public Home() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private FragmentHomeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);

        binding.searchBar.setHint("찾고 싶은 상품을 검색해보세요.");



        return binding.getRoot();
    }

    public void search(View view) {

    }

    public void goWritng(View view) {
        Intent intent = new Intent(view.getContext(), ProductWritng.class);
        startActivity(intent);
    }

}