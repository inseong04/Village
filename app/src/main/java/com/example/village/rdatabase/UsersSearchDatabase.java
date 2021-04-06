package com.example.village.rdatabase;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {UsersSearchData.class}, version = 1)

@TypeConverters({Converts.class})

public abstract class UsersSearchDatabase extends RoomDatabase {
    public abstract  UsersSearchDao usersSearchDao();
}
