package com.example.village.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.village.R;
import com.example.village.databinding.FragmentHomeBinding;
import com.example.village.home.search.SearchActivity;
import com.example.village.productwritng.ProductWriting;
import com.example.village.rdatabase.UserDatabase;

public class Home extends Fragment {

    /*UserDatabase userDatabase = Room.databaseBuilder(this, UserDatabase.class,
            "village-user-db")
            .allowMainThreadQueries()
            .build();

    ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("안녕");
        arrayList.add("ㅁㅁㅁㅁ");
        arrayList.add("ㄴㅁㄹ머ㅓㅓㅓ");

        if(userDatabase.UsersDao().RgetLocation() != null) {
        userDatabase.UsersDao().updateUsers(new Users("ㅇㅇㅇ","ㅁㅁㅁㅁ",arrayList));
    }

        else{
        userDatabase.UsersDao().insertUsers(new Users("옥인성","aaaa",arrayList));
    }

*/


    private FragmentHomeBinding binding;
    private UserDatabase db;
    private HomeViewModel viewModel;
    public Home() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        binding.setActivity(this);

        return binding.getRoot();
    }

    public void go_searchActivity(View view) {
        Intent intent = new Intent(getContext(), SearchActivity.class);
        startActivity(intent);
    }

    public void go_productWriting(View view) {
        Intent intent = new Intent(getContext(), ProductWriting.class);
        startActivity(intent);
    }




}