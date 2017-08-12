package com.henallux.walkandpick.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.henallux.walkandpick.R;

public class FinishActivity extends AppCompatActivity {

    Button Button_GOMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);

        Button_GOMenu = (Button) findViewById(R.id.goMenu);
        Button_GOMenu.setOnClickListener(GoMenu);

    }
    private View.OnClickListener GoMenu = new View.OnClickListener(){
        @Override
        public void onClick(View V)
        {
            startActivity(new Intent(FinishActivity.this, MainActivity.class));
        }
    };
}
