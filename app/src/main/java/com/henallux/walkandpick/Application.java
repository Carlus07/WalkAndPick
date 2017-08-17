package com.henallux.walkandpick;

import android.content.Context;
import android.location.Location;

import com.henallux.walkandpick.Model.Place;

import java.util.ArrayList;

public class Application extends android.app.Application{
    private static String Uri;
    private static String Api;
    private String Token;
    private int order;
    private ArrayList<Place> arrayPlace;

    @Override
    public void onCreate()
    {
        super.onCreate();
        Uri = "http://walkandpickwebapp20170727042830.azurewebsites.net/";
        Api = "api/";
        order = 1;
    }

    public static String getStringApi()
    {
        return Uri + Api;
    }
    public static String getUrl()
    {
        return Uri;
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
            order = 1;
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
