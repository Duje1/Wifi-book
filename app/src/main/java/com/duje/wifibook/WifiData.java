package com.duje.wifibook;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "wifidata_table")
public class WifiData {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String ssid;
    public String securityType;
    public String password;

    public WifiData(String ssid, String securityType, String password) {
        this.ssid = ssid;
        this.securityType = securityType;
        this.password = password;
    }
}
