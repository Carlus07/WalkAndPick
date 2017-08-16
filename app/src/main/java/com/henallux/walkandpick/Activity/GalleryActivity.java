package com.henallux.walkandpick.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuView;
import android.widget.ImageView;
import android.widget.Gallery;


import com.henallux.walkandpick.R;

import java.io.File;

public class GalleryActivity extends AppCompatActivity{

    ImageView gallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gallery = (ImageView) findViewById(R.id.imageView2);

    }


}
