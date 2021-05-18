package com.example.village.home.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.example.village.R;
import com.example.village.databinding.ActivitySearchBinding;
import com.example.village.rdatabase.UserDatabase;
import com.example.village.rdatabase.UsersSearchData;
import com.example.village.rdatabase.UsersSearchDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    //삭제 만들어야함
    ActivitySearchBinding binding;
    private UserDatabase db;
    private SearchViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_search);
        binding.setActivity(this);

        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);



        viewModel.searchWord.observe(this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> arrayList) {

                Log.e("check","asd"+arrayList.get(0));
                if (!(arrayList.isEmpty()) ) {
                    // 실행시 array에 room Rget받아서 넣어줘야함
                    Log.e("beforeUpdate", arrayList.toString());
                    updateDB(arrayList);
                }
            }
        });

        viewModel.searchEtvText.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.searchEtv.setText(s);
            }
        });


        for(String word : converWord(getRoom())) {
            viewModel.setSearchWord(word);
        }
        Log.e("aa",viewModel.searchWord.getValue().toString());
        SearchAdapter adapter = new SearchAdapter(this);
        binding.mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.mRecyclerView.setAdapter(adapter);

        if(viewModel.searchWord.getValue().isEmpty()) {
            binding.mRecyclerView.setVisibility(View.INVISIBLE);
            binding.emptyAlarmTv.setVisibility(View.VISIBLE);
        } else {
            binding.mRecyclerView.setVisibility(View.VISIBLE);
        }

        binding.searchEtv.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(viewModel.searchWord.getValue().isEmpty()) {
                    Log.e("clear","when work searchEtv.setOnclicklistender");
                    viewModel.arrayList.clear();
                }
                viewModel.setSearchWord(binding.searchEtv.getText().toString());
                return true;
            }
        });

        binding.deleteAllTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAll(viewModel.searchWord.getValue());
            }
        });


    }

    public ArrayList<String> converWord(UsersSearchDatabase usersSearchDatabase) {
        ArrayList<String> array = new ArrayList<>();
        String str = usersSearchDatabase.usersSearchDao().RgetSearchWord();

        try {
            str = str.replace("[", "");
            str = str.replace("]", "");
            str = str.replace("\"", "");
            array = new ArrayList<String>(Arrays.asList(str.split(",")));

            return array;
        } catch (NullPointerException e) {
            array.add("");
            return array;
        }


    }

    public UsersSearchDatabase getRoom() {
        final UsersSearchDatabase db = Room.databaseBuilder(this,UsersSearchDatabase.class
                ,"village-search-db")
                .allowMainThreadQueries()
                .build();
    return db;
    }

    public void goHome(View view) {
        finish();
    }

    public void deleteAll(ArrayList<String> searchWords) {
        UsersSearchDatabase db = getRoom();
        Log.w("Search::Room","deleteAll");
        db.usersSearchDao().deleteUsers(new UsersSearchData(searchWords));
    }


    public void updateDB(ArrayList<String> searchWords) {
        UsersSearchDatabase db = getRoom();
        Log.w("Search::Room", "updateDB"+searchWords);
        db.usersSearchDao().updateUsers(new UsersSearchData(searchWords));
    }
    protected void onStop() {
        super.onStop();
        Log.e("onDestroy", getRoom().usersSearchDao().RgetSearchWord());
    }

    public void insertDB(ArrayList<String> searchWords) {
        UsersSearchDatabase db = getRoom();
        Log.w("Search::Room","insertDB");
        db.usersSearchDao().insertUsers(new UsersSearchData(searchWords));
    }
}