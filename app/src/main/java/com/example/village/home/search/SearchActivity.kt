package com.example.village.home.search

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.village.R
import com.example.village.databinding.ActivitySearchBinding
import com.example.village.rdatabase.UsersSearchData
import com.example.village.rdatabase.UsersSearchDatabase
import java.util.*
import kotlin.collections.ArrayList


class SearchActivity : AppCompatActivity() {

    lateinit var binding : ActivitySearchBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_search)
        binding.activity = this@SearchActivity

        var searchWord : MutableList<String> = mutableListOf()

        for(word : String in convertWord(getRoom()))
            searchWord.add(word)

        setSearchWord(searchWord)

        if(searchWord.get(0).equals("")) {
            Log.e("aa", "null");
            binding.mRecyclerView.setVisibility(View.INVISIBLE);
            binding.emptyAlarmTv.setVisibility(View.VISIBLE);
        } else {
            Log.e("aa", "Full");
            binding.mRecyclerView.setVisibility(View.VISIBLE);
        }

        binding.searchEtv.setOnEditorActionListener { v, actionId, event ->
            if (searchWord[0] == "") {
                searchWord.clear()
            }
            searchWord.add(binding.searchEtv.text.toString())
            updateDB(searchWord)
            true
        }

        binding.deleteAllTv.setOnClickListener { deleteAll(searchWord) }
    }

    fun setSearchWord(searchWord : List<String>) {
        val searchAdapter = SearchAdapter(this)
        binding.mRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.mRecyclerView.adapter = searchAdapter
        searchAdapter.data = searchWord

    }
    fun goHome(view: View) {
        finish()
    }
    public fun getRoom() : UsersSearchDatabase {
        val db : UsersSearchDatabase = Room.databaseBuilder(applicationContext,
                UsersSearchDatabase::class.java,
                "serach-db").build()
        return db
    }

    public fun convertWord(usersSearchDatabase: UsersSearchDatabase) : List<String> {
        var array : MutableList<String> = mutableListOf()
        var str : String = usersSearchDatabase.usersSearchDao().RgetSearchWord()

        try {
            str = str.replace("[", "");
            str = str.replace("]", "");
            str = str.replace("\"", "");
            array = Arrays.asList(str.split(",").toString())
            return array
        } catch (e: NullPointerException) {
            array.add("")
            return array
        }
    }

    public fun deleteAll(searchWords: MutableList<String>) {
        val db = getRoom()
        Log.w("Search::Room", "deleteAll")
        db.usersSearchDao().deleteUsers(UsersSearchData(searchWords as java.util.ArrayList<String>?))
    }

    public fun updateDB(searchWords: MutableList<String>) {
        val db : UsersSearchDatabase = getRoom()
        Log.w("Search::Room", "deleteAll")
        db.usersSearchDao().deleteUsers(UsersSearchData(searchWords as java.util.ArrayList<String>?))
    }

    public fun insertDB(searchWords: ArrayList<String?>?) {
        val db = getRoom()
        Log.w("Search::Room", "insertDB")
        db.usersSearchDao().insertUsers(UsersSearchData(searchWords))
    }



}