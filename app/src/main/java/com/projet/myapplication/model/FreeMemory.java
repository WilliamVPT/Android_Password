package com.projet.myapplication.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "free_memory_table")
public class FreeMemory {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private long value;
    private long timestamp;

    public FreeMemory(long value, long timestamp) { this.value = value; this.timestamp = timestamp; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public long getValue() { return value; }
    public void setValue(long value) { this.value = value; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}