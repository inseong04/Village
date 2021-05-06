package com.example.village.rdatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity
public class UsersSearchData {

    @PrimaryKey()
    public int searchID=1;

    @ColumnInfo (name = "searchWord")
    public ArrayList<String> searchWord;

    public ArrayList<String> getSearchWord() {
        return searchWord;
    }

    public void setSearchWord(ArrayList<String> searchWord) {
        this.searchWord = searchWord;
    }

    public UsersSearchData(ArrayList<String> searchWord) {
        this.searchWord = searchWord;
    }
}
