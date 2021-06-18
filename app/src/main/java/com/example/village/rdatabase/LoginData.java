package com.example.village.rdatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class LoginData {

    @PrimaryKey(autoGenerate = true)
    public int loginID;

    @ColumnInfo(name = "id")
    public String dbId;

    @ColumnInfo(name = "password")
    public String dbPassword;



    public LoginData(String dbId, String dbPassword) {
        this.dbId = dbId;
        this.dbPassword = dbPassword;
    }

}
