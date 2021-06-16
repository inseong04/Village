package com.example.village.rdatabase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface LoginDataDao {

    @Insert
    public void insertLogin(LoginData logindata);

    @Update
    public void updataLogin(LoginData logindata);

    @Query("DELETE from LoginData")
    public void deleteLogin();

    @Query("SELECT id FROM LoginData")
    String RgetId();

    @Query("SELECT password FROM LoginData")
    String RgetPassword();

    @Query("SELECT * FROM LoginData")
    List<LoginData> RgetAll();
}
