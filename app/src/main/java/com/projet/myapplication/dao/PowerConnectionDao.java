package com.projet.myapplication.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.projet.myapplication.model.PowerConnection;

@Dao
public interface PowerConnectionDao {
    @Insert
    void insertPowerConnection(PowerConnection powerConnection);

    @Query("SELECT * FROM power_connection_table ORDER BY timestamp DESC LIMIT 1")
    PowerConnection getLastPowerConnection();

    @Query("SELECT * FROM power_connection_table")
    PowerConnection getAllPowerConnection();

    @Update
    void updatePowerConnection(PowerConnection powerConnection);
}
