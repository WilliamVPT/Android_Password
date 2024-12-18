package com.projet.myapplication.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.projet.myapplication.model.FreeMemory;

@Dao
public interface FreeMemoryDao {
    @Insert
    void insertFreeMemory(FreeMemory freeMemory);

    @Query("SELECT * FROM free_memory_table ORDER BY timestamp DESC LIMIT 1")
    FreeMemory getLastFreeMemory();

    //@Query("SELECT * FROM free_memory_table")
    //FreeMemoryDao getAllPowerConnection();

    @Update
    void updateFreeMemory(FreeMemory freeMemory);
}
