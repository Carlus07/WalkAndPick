package com.henallux.walkandpick.Activity;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.google.gson.Gson;
import com.henallux.walkandpick.Application;
import com.henallux.walkandpick.Model.Place;
import com.henallux.walkandpick.R;
import com.henallux.walkandpick.Utility.LocationService;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DetailPlaceActivity extends AppCompatActivity {
    private String mCurrentPhotoPath;
    private Button Button_Picture;
    private TextView detailPlace;
    private TextView titlePlace;
    private Application app;
    private File photoFile;
    private ImageView imagePlace;
    private Place place;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        app = (Application) getApplicationContext();
        place = app.getPlaceCurrent();
        if (place == null) startActivity(new Intent(this, FinishActivity.class));
        else
        {
            setContentView(R.layout.activity_detailplace);
            deleteNotification();

            imagePlace = (ImageView) findViewById(R.id.imagePlace);
            detailPlace = (TextView) findViewById(R.id.placeDescription);
            titlePlace = (TextView) findViewById(R.id.placeTitle);

            Button_Picture = (Button) findViewById(R.id.goPicture);
            Button_Picture.setOnClickListener(GoPicture);

            String uri = "@drawable/"+place.getPicture();
            int imageResource = getResources().getIdentifier(uri, null, getPackageName());
            Drawable res = getResources().getDrawable(imageResource);

            imagePlace.setImageDrawable(res);
            detailPlace.setText(place.getDescription());
            titlePlace.setText(place.getName());
        }
    }

    private View.OnClickListener GoPicture = new View.OnClickListener(){
        @Override
        public void onClick(View V)
        {
            dispatchTakePictureIntent();
        }
    };

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(photoFile != null){
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            galleryAddPic();

            new uploadPicture().execute();

            place = app.getPlace();

            //Localisation
            Intent intent = new Intent(DetailPlaceActivity.this, LocationService.class);
            intent.putExtra("Place", place.getGpsAdress());
            startService(intent);

            //Google map
            Uri gmmIntentUri = Uri.parse("google.navigation:q="+place.getGpsAdress()+"&mode=w");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        }
        else{
            //...
        }
    }

    private class uploadPicture extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            Map config = new HashMap();
            config.put("cloud_name", "djxtxdr8l");
            config.put("api_key", "161485146941155");
            config.put("api_secret", "CNfky6G5j1dHvj2svdUMrLg3hEQ");
            Cloudinary cloudinary = new Cloudinary(config);
            try {
                Application app = (Application) getApplicationContext();
                Map x = cloudinary.uploader().upload(mCurrentPhotoPath, ObjectUtils.emptyMap());
                Log.i("Test",x.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
    private void deleteNotification(){
        final NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(2017);
    }
}
