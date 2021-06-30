package com.example.bloodbank.data.local.Room;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.bloodbank.data.model.PostsData.Datum;


@Database(entities = {Datum.class}, version = 2, exportSchema = false)
@TypeConverters(RoomConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "ArticleDataBase";
    private static AppDatabase sInstance;

    public abstract RoomDao PostDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            sInstance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, AppDatabase.DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return sInstance;
    }


}

