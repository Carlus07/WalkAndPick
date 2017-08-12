package com.henallux.walkandpick.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.henallux.walkandpick.Application;
import com.henallux.walkandpick.DataAccess.CourseDAO;
import com.henallux.walkandpick.Model.Course;
import com.henallux.walkandpick.R;
import com.henallux.walkandpick.Utility.CoursesAdapter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Max on 8/11/2017.
 */

public class PictureActivity extends AppCompatActivity {
    ImageView imageView ;
    Button Button_NextPlace;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        Button_NextPlace = (Button) findViewById(R.id.goNext);
        Button_NextPlace.setOnClickListener(GoNext);
        imageView= (ImageView) findViewById(R.id.picture);

        byte[] byteArray = getIntent().getByteArrayExtra("bitmap");
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        imageView.setImageBitmap(bmp);
    }

    private View.OnClickListener GoNext = new View.OnClickListener(){
        @Override
        public void onClick(View V)
        {
            startActivity(new Intent(PictureActivity.this, MainActivity.class));
        }
    };


}
