package com.projet.myapplication.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "power_connection_table")
public class PowerConnection {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private long timestamp;

    public PowerConnection(long timestamp) { this.timestamp = timestamp; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}
