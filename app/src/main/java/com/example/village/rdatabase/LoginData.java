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

    public int getLoginID() {
        return loginID;
    }

    public LoginData(String dbId, String dbPassword) {
        this.dbId = dbId;
        this.dbPassword = dbPassword;
    }

    public void setLoginID(int loginID) {
        this.loginID = loginID;
    }

    public String getDbId() {
        return dbId;
    }

    public void setDbId(String dbId) {
        this.dbId = dbId;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }
}
