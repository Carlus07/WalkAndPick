package com.henallux.walkandpick.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.henallux.walkandpick.Application;
import com.henallux.walkandpick.DataAccess.UserDAO;
import com.henallux.walkandpick.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.R.attr.bitmap;

public class LogActivity extends AppCompatActivity implements TextWatcher {

    Button Button_Connection;
    Button Button_Register;
    EditText MailConnection, PasswordConnection;

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        Button_Connection = (Button) findViewById(R.id.connection);
        Button_Connection.setOnClickListener(Connection);

        Button_Register = (Button) findViewById(R.id.newAccount);
        Button_Register.setOnClickListener(GoRegister);

        MailConnection = (EditText) findViewById(R.id.mailConnection);
        MailConnection.addTextChangedListener(this);

        PasswordConnection = (EditText) findViewById(R.id.passwordConnection);
        PasswordConnection.addTextChangedListener(this);
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private View.OnClickListener Connection = new View.OnClickListener(){
        @Override
        public void onClick(View V)
        {
           if ((!MailConnection.getText().toString().equals("")) && (!PasswordConnection.getText().toString().equals("")))
            {
                Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(MailConnection.getText().toString());
                if (matcher.find())
                {
                    String mailTxt = MailConnection.getText().toString();
                    String passwordTxt = PasswordConnection.getText().toString();
                    new ConnectionDB().execute(mailTxt,passwordTxt);
                }
                else Toast.makeText(LogActivity.this, R.string.mailInvalid , Toast.LENGTH_SHORT).show();
            }
            else Toast.makeText(LogActivity.this, R.string.emptyField, Toast.LENGTH_SHORT).show();
        }
    };

    private View.OnClickListener GoRegister = new View.OnClickListener(){
        @Override
        public void onClick(View V)
        {
            startActivity(new Intent(LogActivity.this, RegisterActivity.class));
        }
    };

    private class ConnectionDB extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String...params){
            UserDAO userDAO = new UserDAO();
            String token=null;
            try{
                token = userDAO.Connection(params[0],params[1]);
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

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
