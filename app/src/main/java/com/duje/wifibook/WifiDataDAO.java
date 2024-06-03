package com.duje.wifibook;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface WifiDataDAO {
    @Insert
    void insert(WifiData wifiData);

    @Update
    void update(WifiData wifiData);

    @Delete
    void delete(WifiData wifiData);

    @Query("SELECT * FROM wifidata_table WHERE id = :id LIMIT 1")
    WifiData getWifiDataById(int id);

    @Query("SELECT * FROM wifidata_table")
    List<WifiData> getAllWifiData();
}
