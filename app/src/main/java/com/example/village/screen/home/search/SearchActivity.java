package com.example.village.screen.home.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.example.village.R;
import com.example.village.databinding.ActivitySearchBinding;
import com.example.village.screen.home.searchresult.SearchResult;
import com.example.village.rdatabase.UsersSearchData;
import com.example.village.rdatabase.UsersSearchDatabase;

import java.util.ArrayList;
import java.util.Arrays;

public class SearchActivity extends AppCompatActivity {

    ActivitySearchBinding binding;
    private SearchViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        binding.setActivity(this);

        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);

        viewModel.searchWord.observe(this, arrayList -> {
            if (viewModel.first) {
                if (!viewModel.searchWordDeleteIndex0.getValue())
                    insertDB(arrayList);
                else
                    viewModel.searchWordDeleteIndex0.setValue(false);

            } else {
                updateDB(arrayList);
            }
        });

        viewModel.searchWordDeleteIndex0.observe(this, bl -> {
            if (bl) {
                deleteAll(viewModel.searchWord.getValue());
            }
        });

        viewModel.searchEtvText.observe(this, s -> binding.searchEtv.setText(s));

        viewModel.emptyAlarm.observe(this, bl -> {
            if (bl) {
                binding.mRecyclerView.setVisibility(View.INVISIBLE);
                binding.emptyAlarmTv.setVisibility(View.VISIBLE);
            }
        });

        for (String word : converWord(getRoom())) {
            if (word.equals(""))
                break;
            viewModel.setSearchWord(word);
        }

        SearchAdapter adapter = new SearchAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        binding.mRecyclerView.setLayoutManager(linearLayoutManager);
        binding.mRecyclerView.setAdapter(adapter);

        if (viewModel.searchWord.getValue() == null) {
            viewModel.first = true;
            viewModel.emptyAlarm.setValue(true);
        } else {
            binding.mRecyclerView.setVisibility(View.VISIBLE);
        }

        binding.searchEtv.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                viewModel.setSearchWord(binding.searchEtv.getText().toString());
                Intent intent = new Intent(getApplicationContext(), SearchResult.class);
                intent.putExtra("searchWord", binding.searchEtv.getText().toString());
                startActivity(intent);
                finish();
                return true;
            }
        });

        binding.deleteAllTv.setOnClickListener(v -> {
            deleteAll(viewModel.searchWord.getValue());
            viewModel.first = true;
            viewModel.emptyAlarm.setValue(true);
            viewModel.arrayList = new ArrayList<>();
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
        final UsersSearchDatabase db = Room.databaseBuilder(this, UsersSearchDatabase.class
                , "village-search-db")
                .allowMainThreadQueries()
                .build();
        return db;
    }

    public void goHome(View view) {
        finish();
    }

    public void deleteAll(ArrayList<String> searchWords) {
        UsersSearchDatabase db = getRoom();
        Log.w("Search::Room", "deleteAll");
        db.usersSearchDao().deleteUsers(new UsersSearchData(searchWords));
    }



    public void updateDB(ArrayList<String> searchWords) {
        UsersSearchDatabase db = getRoom();
        Log.w("Search::Room", "updateDB" + searchWords);
        db.usersSearchDao().updateUsers(new UsersSearchData(searchWords));
    }

    public void insertDB(ArrayList<String> searchWords) {
        UsersSearchDatabase db = getRoom();
        Log.w("Search::Room", "insertDB");
        db.usersSearchDao().insertUsers(new UsersSearchData(searchWords));
    }
}