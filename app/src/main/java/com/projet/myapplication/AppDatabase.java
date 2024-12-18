package com.projet.myapplication;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.projet.myapplication.dao.FreeMemoryDao;
import com.projet.myapplication.dao.PowerConnectionDao;
import com.projet.myapplication.model.FreeMemory;
import com.projet.myapplication.model.PowerConnection;

@Database(entities = {PowerConnection.class, FreeMemory.class}, version = 6, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;
    public abstract PowerConnectionDao powerConnectionDao();
    public abstract FreeMemoryDao freeMemoryDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase.class,
                    "app_database"
            ).fallbackToDestructiveMigration().build();
        }
        return instance;
    }
}
