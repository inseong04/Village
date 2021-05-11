package com.example.village.home.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_search);
        binding.setActivity(this);
        ArrayList<String> searchWord = new ArrayList<>();




        for(String word : converWord(getRoom())) {
            searchWord.add(word);
        }
        Log.e("aa",searchWord.toString());
        SearchAdapter adapter = new SearchAdapter(searchWord);
        binding.mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.mRecyclerView.setAdapter(adapter);
        if(searchWord.get(0).equals("")) {
            Log.e("aa","null");
            binding.mRecyclerView.setVisibility(View.INVISIBLE);
            binding.emptyAlarmTv.setVisibility(View.VISIBLE);
        } else {
            Log.e("aa","Full");
            binding.mRecyclerView.setVisibility(View.VISIBLE);
        }

        binding.searchEtv.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(searchWord.get(0).equals("")) {
                    searchWord.clear();
                }
                searchWord.add(binding.searchEtv.getText().toString());
                updateDB(searchWord);

                return true;
            }
        });

        binding.searchEtv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(binding.searchEtv.getText().
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.deleteAllTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAll(searchWord);
            }
        });

    }

    public List<String> converWord(UsersSearchDatabase usersSearchDatabase) {
        List<String> array = new ArrayList<>();
        String str = usersSearchDatabase.usersSearchDao().RgetSearchWord();

        try {
            str = str.replace("[", "");
            str = str.replace("]", "");
            str = str.replace("\"", "");
            array = Arrays.asList(str.split(","));
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
        Log.w("Search::Room", "updateDB");
        db.usersSearchDao().updateUsers(new UsersSearchData(searchWords));
    }

    public void insertDB(ArrayList<String> searchWords) {
        UsersSearchDatabase db = getRoom();
        Log.w("Search::Room","insertDB");
        db.usersSearchDao().insertUsers(new UsersSearchData(searchWords));
    }
}