package com.henallux.walkandpick.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.henallux.walkandpick.R;

public class LogActivity extends AppCompatActivity {

    Button Button_Connection;
    Button Button_Register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        Button_Connection = (Button) findViewById(R.id.connection);
        Button_Connection.setOnClickListener(Connection);

        Button_Register = (Button) findViewById(R.id.newAccount);
        Button_Register.setOnClickListener(GoRegister);
    }
    private View.OnClickListener Connection = new View.OnClickListener(){
        @Override
        public void onClick(View V)
        {
            startActivity(new Intent(LogActivity.this, MainActivity.class));
        }
    };
    private View.OnClickListener GoRegister = new View.OnClickListener(){
        @Override
        public void onClick(View V)
        {
            startActivity(new Intent(LogActivity.this, RegisterActivity.class));
        }
    };
}
