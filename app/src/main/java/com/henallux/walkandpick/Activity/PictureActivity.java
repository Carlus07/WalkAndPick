package com.henallux.walkandpick.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.henallux.walkandpick.Application;
import com.henallux.walkandpick.DataAccess.CourseDAO;
import com.henallux.walkandpick.Model.Course;
import com.henallux.walkandpick.R;
import com.henallux.walkandpick.Utility.CoursesAdapter;

import java.util.ArrayList;

/**
 * Created by Max on 8/11/2017.
 */

public class PictureActivity extends AppCompatActivity {
    ImageView imageView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        imageView= (ImageView) findViewById(R.id.picture);

        byte[] byteArray = getIntent().getByteArrayExtra("bitmap");
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        imageView.setImageBitmap(bmp);
    }
}
