package com.example.village.rdatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity
public class Users {
    @PrimaryKey (autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "name")
    public String dbName;

    @ColumnInfo(name = "id")
    public String dbId;

    @ColumnInfo(name = "password")
    public String dbPassword;

    @ColumnInfo(name = "location")
    public String dbLocation;

    @ColumnInfo(name = "searchWord")
    public ArrayList<String> dbSearchWord;



}
