package com.example.village.rdatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity
public class Users {
    @PrimaryKey
    public int usersID = 1;

    @ColumnInfo(name = "name")
    public String dbName;

    @ColumnInfo(name = "location")
    public String dbLocation;

/*    @ColumnInfo(name = "searchWord")
    public ArrayList<String> dbSearchWord;*/



}
