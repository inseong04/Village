package com.example.village.rdatabase;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Users.class}, version = 1)

@TypeConverters({Converts.class})

public abstract class UserDatabase extends RoomDatabase {
    public abstract UsersDao UsersDao();

}
