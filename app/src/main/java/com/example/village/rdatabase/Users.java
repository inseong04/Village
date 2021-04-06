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

    public Users(String dbName, String dbLocation) {
        this.dbName = dbName;
        this.dbLocation = dbLocation;
    }

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
}


