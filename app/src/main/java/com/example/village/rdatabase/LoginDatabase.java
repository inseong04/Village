package com.example.village.rdatabase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {LoginData.class}, version = 1)
public abstract class LoginDatabase extends RoomDatabase {
    public abstract LoginDataDao LoginDataDao();
}
