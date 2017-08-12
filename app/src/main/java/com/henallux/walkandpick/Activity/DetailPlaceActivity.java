package com.henallux.walkandpick.Activity;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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

public class DetailPlaceActivity extends AppCompatActivity {
    String mCurrentPhotoPath;
    Button Button_Picture;
    TextView detailPlace;
    Application app;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView imagePlace;
    Place place;

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

            Button_Picture = (Button) findViewById(R.id.goPicture);
            Button_Picture.setOnClickListener(GoPicture);
            //String listSerializedToJson = getIntent().getExtras().getString("places");
            //ArrayList<Place> places = new Gson().fromJson(listSerializedToJson, ArrayList.class);
            //Intent i = getIntent();
            //Place place = (Place) i.getSerializableExtra("place");

            detailPlace.setText(place.getDescription());
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
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(photoFile != null){
                Uri photoURI =FileProvider.getUriForFile(this,
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

            galleryAddPic();

            place = app.getPlace();

            Intent intent = new Intent(DetailPlaceActivity.this, LocationService.class);
            intent.putExtra("Place", place.getGpsAdress());
            startService(intent);

            Uri gmmIntentUri = Uri.parse("google.navigation:q="+place.getGpsAdress()+"&mode=w");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);

            /*ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            Intent intent = new Intent(DetailPlaceActivity.this, PictureActivity.class);
            intent.putExtra("bitmap", byteArray);
            startActivity(intent);*/
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
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = /*Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES); */getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
    private void deleteNotification(){
        final NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(2017);
    }
}
