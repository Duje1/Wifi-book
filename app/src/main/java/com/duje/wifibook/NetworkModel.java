package com.duje.wifibook;

public class NetworkModel {
    String SSID;
    String SecurityType;
    String Password;

    public NetworkModel(String SSID, String securityType, String password) {
        this.SSID = SSID;
        this.SecurityType = securityType;
        this.Password = password;
    }

    public String getSSID() {
        return SSID;
    }

    public String getSecurityType() {
        return SecurityType;
    }

    public String getPassword() {
        return Password;
    }
}
