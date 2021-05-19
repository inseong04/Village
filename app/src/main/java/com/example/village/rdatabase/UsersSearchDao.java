package com.example.village.rdatabase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UsersSearchDao {

    @Insert
    public void insertUsers(UsersSearchData usersSearchData);

    @Update
    public void updateUsers(UsersSearchData usersSearchData);

    @Delete
    public void deleteUsers(UsersSearchData usersSearchData);

    @Query("SELECT searchWord FROM UsersSearchData")
    String RgetSearchWord();

}
