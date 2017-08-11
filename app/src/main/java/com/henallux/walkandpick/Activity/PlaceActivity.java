package com.henallux.walkandpick.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import android.widget.Button;
import android.widget.ListView;

import com.henallux.walkandpick.Application;
import com.henallux.walkandpick.DataAccess.PlaceDAO;
import com.henallux.walkandpick.Model.Place;
import com.henallux.walkandpick.R;
import com.henallux.walkandpick.Utility.LocationService;
import com.henallux.walkandpick.Utility.PlacesAdapter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PlaceActivity extends AppCompatActivity{
    private ListView ListView_Places;
    private int idCourse;
    Button Button_GoCourse;
    String mCurrentPhotoPath;
    private double latitude;
    private double longitude;
    private Place firstPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        ListView_Places = (ListView) findViewById(R.id.listItemPlace);

        Intent intent = getIntent();
        idCourse = intent.getIntExtra("idCourse", 0);

        Button_GoCourse = (Button) findViewById(R.id.goCourse);
        Button_GoCourse.setOnClickListener(GoCourse);

        new LoadPlaces().execute();
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private View.OnClickListener GoCourse = new View.OnClickListener() {
        @Override
        public void onClick(View V)
        {
            Uri gmmIntentUri = Uri.parse("google.navigation:q="+firstPlace.getGpsAdress()+"&mode=w");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
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
            firstPlace = places.get(0);

            Intent intent = new Intent(PlaceActivity.this, LocationService.class);
            intent.putExtra("Place", firstPlace.getGpsAdress());
            startService(intent);
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(photoFile != null){
                Application app = (Application) getApplicationContext();

                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.henallux.walkandpick.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
            int i = bitmap.getHeight();
            int x = bitmap.getWidth();
            String t = bitmap.toString();

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            Intent intent = new Intent(PlaceActivity.this, PictureActivity.class);
            intent.putExtra("bitmap", byteArray);
            startActivity(intent);
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
}
