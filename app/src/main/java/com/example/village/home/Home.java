package com.example.village.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.village.R;
import com.example.village.databinding.FragmentHomeBinding;
import com.example.village.productwritng.ProductWritng;
import com.example.village.rdatabase.UserDatabase;
import com.example.village.rdatabase.Users;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


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

    String abc = userDatabase.UsersDao().RgetSearchWord();
    abc = abc.replace("[","");
    abc = abc.replace("]","");
    abc = abc.replace("\"","");
    List<String> arrayList1 = Arrays.asList(abc.split(","));
        Log.e("ba",arrayList1.get(1));*/


    private FragmentHomeBinding binding;
    private UserDatabase db;

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

        binding.searchBar.setHint("찾고 싶은 상품을 검색해보세요.");

        binding.floatingWriting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),ProductWritng.class);
                startActivity(intent);
            }
        });

        return binding.getRoot();
    }

    private void updateUsers() {

    }



}