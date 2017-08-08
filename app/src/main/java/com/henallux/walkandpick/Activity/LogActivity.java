package com.henallux.walkandpick.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.henallux.walkandpick.Application;
import com.henallux.walkandpick.DataAccess.UserDAO;
import com.henallux.walkandpick.R;

public class LogActivity extends AppCompatActivity {

    Button Button_Connection;
    String mailTxt;
    String passwordTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        Button_Connection = (Button) findViewById(R.id.connection);
        Button_Connection.setOnClickListener(Test);
    }

    private View.OnClickListener Test = new View.OnClickListener(){
        @Override
        public void onClick(View V)
        {
            TextView mail = (TextView)findViewById(R.id.mail);
            mailTxt = mail.getText().toString();
            TextView password =  (TextView) findViewById(R.id.password);
            passwordTxt = password.getText().toString();
            new Connection().execute();
        }
    };

    private class Connection extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String...params){
            UserDAO userDAO = new UserDAO();
            String token=null;
            try{
                token = userDAO.Connection(mailTxt,passwordTxt);
            }
            catch (Exception e){
               e.printStackTrace();
            }
            return token;
        }

        @Override
        protected void onPostExecute(String token){
            if(token!=null){
                Application appObject = (Application) getApplicationContext();
                appObject.setToken(token);
                startActivity(new Intent(LogActivity.this, MainActivity.class));
            }
        }
    }
}
