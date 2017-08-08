package com.henallux.walkandpick.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.henallux.walkandpick.Application;
import com.henallux.walkandpick.DataAccess.PlaceDAO;
import com.henallux.walkandpick.DataAccess.UserDAO;
import com.henallux.walkandpick.Model.Place;

import java.util.ArrayList;

public class PlaceActivity extends AppCompatActivity{
    private ListView placeList;

    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //...
        new LoadPlaces().execute();
    }


    private class LoadPlaces extends AsyncTask<String, Void, ArrayList<Place>>
    {
        @Override
        protected ArrayList<Place> doInBackground(String...params){
            Application app = (Application) getApplicationContext();
            String token = app.getToken();
            PlaceDAO placeDAO = new PlaceDAO();
            ArrayList<Place> places = new ArrayList<>();
            try{
                places = placeDAO.getAllPlacesFromTheCourse(1/*ID de la course*/, token);
            }
            catch (Exception e){
                e.printStackTrace();
            }
            return places;
        }

        @Override
        protected void onPostExecute(ArrayList<Place> places){
            //ArrayAdapter<Place> adapter = new ArrayAdapter<Place>(this, /*Layout de places*/, places.toString());
            //placeList.setAdapter(adapter);
        }
    }
}
