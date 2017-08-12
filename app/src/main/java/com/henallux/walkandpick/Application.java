package com.henallux.walkandpick;

import android.location.Location;

import com.henallux.walkandpick.Model.Place;

import java.util.ArrayList;

public class Application extends android.app.Application{
    private String Uri;
    private String Api;
    private String Token;
    private int order;
    private ArrayList<Place> arrayPlace;

    @Override
    public void onCreate()
    {
        super.onCreate();
        Uri = "http://walkandpickwebapp20170727042830.azurewebsites.net/";
        Api = "api/";
        order = 0;
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

    public Place getPlace()
    {
        if (order <= arrayPlace.size())
        {
            Place place = arrayPlace.get(order);
            order++;
            return place;
        }
        else
        {
            order = 0;
            return null;
        }
    }
    public void setPlaces(ArrayList places)
    {
        order = 0;
        arrayPlace = places;
    }
    public Place getPlaceCurrent()
    {
        return arrayPlace.get(order);
    }
}
