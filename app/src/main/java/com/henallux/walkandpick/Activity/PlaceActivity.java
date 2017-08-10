package com.henallux.walkandpick.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.henallux.walkandpick.Application;
import com.henallux.walkandpick.DataAccess.PlaceDAO;
import com.henallux.walkandpick.Model.Place;
import com.henallux.walkandpick.R;
import com.henallux.walkandpick.Utility.PlacesAdapter;

import java.util.ArrayList;

public class PlaceActivity extends AppCompatActivity{
    private ListView ListView_Places;
    private int idCourse;
    Button Button_GoCourse;

    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        ListView_Places = (ListView) findViewById(R.id.listItemPlace);

        Intent intent = getIntent();
        idCourse = intent.getIntExtra("idCourse", 0);

        Button_GoCourse = (Button) findViewById(R.id.goCourse);
        Button_GoCourse.setOnClickListener(GoCourse);

        new LoadPlaces().execute();
    }

    private View.OnClickListener GoCourse = new View.OnClickListener() {
        @Override
        public void onClick(View V) {
            Toast.makeText(PlaceActivity.this, "Test" , Toast.LENGTH_SHORT).show();
        }
    };
    private class LoadPlaces extends AsyncTask<String, Void, ArrayList<Place>>
    {
        @Override
        protected ArrayList<Place> doInBackground(String...params){
            Application app = (Application) getApplicationContext();
            String token = app.getToken();
            PlaceDAO placeDAO = new PlaceDAO();
            ArrayList<Place> places = new ArrayList<>();
            try{
                places = placeDAO.getAllPlacesFromTheCourse(idCourse, token);
            }
            catch (Exception e){
                e.printStackTrace();
            }
            return places;
        }

        @Override
        protected void onPostExecute(ArrayList<Place> places){
            // Création et initialisation de l'Adapter pour les Listes
            PlacesAdapter adapter = new PlacesAdapter(PlaceActivity.this, places);
            // Initialisation de la liste avec les données
            ListView_Places.setAdapter(adapter);
        }
    }
}
