package com.henallux.walkandpick;

public class Application extends android.app.Application{
    private String Uri;
    private String Api;
    private String Token;

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
}
