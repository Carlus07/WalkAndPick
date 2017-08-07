package com.henallux.walkandpick.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.henallux.walkandpick.R;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity
{
    private static String TAG = MenuActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }
}
