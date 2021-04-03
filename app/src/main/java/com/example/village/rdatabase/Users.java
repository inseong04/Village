package com.example.village.rdatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.Gson;

import java.util.ArrayList;

@Entity
public class Users {
    @PrimaryKey
    public int usersID = 1;

    @ColumnInfo(name = "name")
    public String dbName;

    @ColumnInfo(name = "location")
    public String dbLocation;

    @ColumnInfo(name = "searchWord")
    public ArrayList<String> searchWord;

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getDbLocation() {
        return dbLocation;
    }

    public void setDbLocation(String dbLocation) {
        this.dbLocation = dbLocation;
    }

    public ArrayList<String> getSearchWord() {
        return searchWord;
    }

    public void setSearchWord(ArrayList<String> searchWord) {
        this.searchWord = searchWord;
    }

    public Users(String dbName, String dbLocation, ArrayList<String> searchWord) {
        this.dbName = dbName;
        this.dbLocation = dbLocation;
        this.searchWord = searchWord;
    }

    /*    @ColumnInfo(name = "searchWord")
    public ArrayList<String> dbSearchWord;*/



}
