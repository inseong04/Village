package com.example.village.rdatabase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UsersDao {


    @Insert
    public void insertUsers(Users users);

    @Update
    public void updateUsers(Users users);

    @Delete
    public void deleteUsers(Users users);

    @Query("SELECT * FROM Users ")
    List<Users> getAll();

}
