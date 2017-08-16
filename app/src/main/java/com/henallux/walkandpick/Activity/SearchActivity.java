package com.henallux.walkandpick.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.henallux.walkandpick.Application;
import com.henallux.walkandpick.DataAccess.PlaceDAO;
import com.henallux.walkandpick.Model.Course;
import com.henallux.walkandpick.Model.Place;
import com.henallux.walkandpick.Model.Search;
import com.henallux.walkandpick.R;
import com.henallux.walkandpick.Utility.ErrorUtility;
import com.henallux.walkandpick.Utility.LocationService;
import com.henallux.walkandpick.Utility.PlacesAdapter;

import java.util.ArrayList;

public class SearchActivity  extends AppCompatActivity {

    private RadioButton easy, medium, difficult;
    private int difficulty = 1;
    private Button buttonSearch;
    private SeekBar seekBar;
    private TextView kilometer;
    private Application app;
    private Spinner spinner;
    private PlaceDAO placeDAO;
    private ErrorUtility errorUtility;

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

        buttonSearch = (Button) findViewById(R.id.searchPlace);
        buttonSearch.setOnClickListener(Search);

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

    private View.OnClickListener Search = new View.OnClickListener(){
        @Override
        public void onClick(View V)
        {
            Intent searchIntent = new Intent(SearchActivity.this, CourseActivity.class);
            Place place = (Place) ( (Spinner) findViewById(R.id.spinner) ).getSelectedItem();
            String idPlace = (place == null) ? 0+"" : place.getId()+"";
            searchIntent.putExtra("search", new Search(difficulty+"",seekBar.getProgress()+"", idPlace));
            startActivity(searchIntent);
        }
    };

    private class LoadPlaces extends AsyncTask<String, Void, ArrayList<Place>>
    {
        @Override
        protected ArrayList<Place> doInBackground(String...params){
            String token = app.getToken();
            placeDAO = new PlaceDAO();
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
            errorUtility = new ErrorUtility();
            if (placeDAO.getError() == 0) {
                Place place = new Place();
                places.add(0, place);
                ArrayAdapter placeAdapter = new ArrayAdapter(SearchActivity.this, R.layout.spinner, places);
                spinner.setAdapter(placeAdapter);
            }
            else Toast.makeText(SearchActivity.this, errorUtility.getError(placeDAO.getError()), Toast.LENGTH_SHORT).show();
        }
    }
}
