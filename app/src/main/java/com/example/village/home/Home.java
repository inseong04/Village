package com.example.village.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.village.R;
import com.example.village.databinding.FragmentHomeBinding;
import com.example.village.productwritng.ProductWritng;
import com.example.village.rdatabase.UserDatabase;
import com.example.village.rdatabase.UsersSearchData;
import com.example.village.rdatabase.UsersSearchDatabase;
import com.mancj.materialsearchbar.MaterialSearchBar;
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

        ArrayList<String> searchWord = new ArrayList<>();
        final UsersSearchDatabase db = Room.databaseBuilder(getContext(),UsersSearchDatabase.class
                ,"village-search-db")
                .allowMainThreadQueries()
                .build();
        final UserDatabase userDatabase = Room.databaseBuilder(getContext(),UserDatabase.class
        ,"village-user-db")
                .allowMainThreadQueries()
                .build();


        binding.setLifecycleOwner(this);
        viewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        binding.setViewModel(viewModel);
        binding.searchBar.setHint("찾고 싶은 상품을 검색해보세요.");

        for(String word : converWord(db)) {
            searchWord.add(word);
        }

        binding.floatingWriting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),ProductWritng.class);
                startActivity(intent);
            }
        });



        SearchAdapter adapter = new SearchAdapter(searchWord);
        binding.mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.mRecyclerView.setAdapter(adapter);

        binding.searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if(enabled){
                    viewModel.setVisible();
                } else {
                    viewModel.setInvisible();
                }
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                Toast.makeText(getContext(), "qwtgeqa", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });

        binding.searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.setInvisible();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        return binding.getRoot();
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

    private void updateUsers() {

    }

    public void insertDB(UsersSearchDatabase db, ArrayList<String> searchWords) {
        Log.w("Search::Room","insertDB");
        db.usersSearchDao().insertUsers(new UsersSearchData(searchWords));
    }

}