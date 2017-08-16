package com.henallux.walkandpick.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.henallux.walkandpick.Application;
import com.henallux.walkandpick.DataAccess.PlaceDAO;
import com.henallux.walkandpick.Model.Place;
import com.henallux.walkandpick.R;
import com.henallux.walkandpick.Utility.LocationService;
import com.henallux.walkandpick.Utility.PlacesAdapter;

import java.util.ArrayList;

public class SearchActivity  extends AppCompatActivity {

    RadioButton easy, medium, difficult;
    private int difficulty = 1;
    private SeekBar seekBar;
    private TextView kilometer;
    private Application app;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        app = (Application) getApplicationContext();

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        kilometer = (TextView) findViewById(R.id.titleSeekBar);
        spinner = (Spinner) findViewById(R.id.spinner);

        kilometer.setText("3 km");
        seekBar.setProgress(3);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            int progress = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                kilometer.setText(seekBar.getProgress() + "km");
            }
        });
        easy= (RadioButton) findViewById(R.id.easy);
        medium = (RadioButton) findViewById(R.id.medium);
        difficult = (RadioButton) findViewById(R.id.difficult);
        easy.setChecked(true);
        easy.setOnClickListener(radio_listener);
        medium.setOnClickListener(radio_listener);
        difficult.setOnClickListener(radio_listener);

        new LoadPlaces().execute();
    }

    private View.OnClickListener radio_listener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.easy:
                    difficulty = 1;
                    break;
                case R.id.medium:
                    difficulty = 2;
                    break;
                case R.id.difficult:
                    difficulty = 3;
                    break;
            }
        }
    };

    private class LoadPlaces extends AsyncTask<String, Void, ArrayList<Place>>
    {
        @Override
        protected ArrayList<Place> doInBackground(String...params){
            String token = app.getToken();
            PlaceDAO placeDAO = new PlaceDAO();
            ArrayList<Place> places = new ArrayList<>();
            try{
                places = placeDAO.getAllPlaces(token);
            }
            catch (Exception e){
                e.printStackTrace();
            }
            return places;
        }

        @Override
        protected void onPostExecute(ArrayList<Place> places){
            ArrayAdapter placeAdapter = new ArrayAdapter(SearchActivity.this, R.layout.spinner, places);
            spinner.setAdapter(placeAdapter);
            Place user = (Place) ( (Spinner) findViewById(R.id.spinner) ).getSelectedItem();
        }
    }
}
