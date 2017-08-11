package com.henallux.walkandpick;

import android.location.Location;

public class Application extends android.app.Application{
    private String Uri;
    private String Api;
    private String Token;
    private Location Location;

    @Override
    public void onCreate()
    {
        super.onCreate();
        Uri = "http://walkandpickwebapp20170727042830.azurewebsites.net/";
        Api = "api/";
    }
    public String getStringApi()
    {
        return Uri + Api;
    }
    public String getToken()
    {
        return Token;
    }
    public void setToken(String token)
    {
        Token = token;
    }
    public void setLocation(Location location) {Location = location;}
    public Location getLocation() { return Location;}
}
