package com.henallux.walkandpick.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.henallux.walkandpick.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity implements TextWatcher {
    Button Button_Register;
    EditText Name, FirstName, Mail, Password, PasswordBis, City;
    RadioButton radio_H, radio_F;
    Boolean gender = true;

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static final Pattern VALID_PASSWORD_REGEX =
            Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button_Register = (Button) findViewById(R.id.registerAccount);
        Button_Register.setOnClickListener(Register);

        Name = (EditText) findViewById(R.id.name);
        Name.addTextChangedListener(this);

        FirstName = (EditText) findViewById(R.id.firtName);
        FirstName.addTextChangedListener(this);

        radio_H = (RadioButton) findViewById(R.id.man);
        radio_F = (RadioButton) findViewById(R.id.woman);

        radio_H.setOnClickListener(radio_listener);
        radio_F.setOnClickListener(radio_listener);

        Mail = (EditText) findViewById(R.id.mail);
        Mail.addTextChangedListener(this);

        Password = (EditText) findViewById(R.id.password);
        Password.addTextChangedListener(this);

        PasswordBis = (EditText) findViewById(R.id.passwordbis);
        PasswordBis.addTextChangedListener(this);

        City = (EditText) findViewById(R.id.city);
        City.addTextChangedListener(this);
    }

    private View.OnClickListener Register = new View.OnClickListener()
    {
        @Override
        public void onClick(View V) {
            if ((!Name.getText().toString().equals("")) &&
                    (!FirstName.getText().toString().equals("")) &&
                    (!Mail.getText().toString().equals("")) &&
                    (!Password.getText().toString().equals("")) &&
                    (!PasswordBis.getText().toString().equals("")))
            {
                Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(Mail.getText().toString());
                if (matcher.find())
                {
                    matcher = VALID_PASSWORD_REGEX.matcher(Password.getText().toString());
                    if (matcher.find())
                    {
                        if(Password.getText().toString().equals(PasswordBis.getText().toString()))
                        {
                            //------------------------------------------------
                            //          A completer avec l'accès à la bd
                            //------------------------------------------------
                            startActivity(new Intent(RegisterActivity.this, LogActivity.class));
                        }
                        else Toast.makeText(RegisterActivity.this, R.string.passwordNotSame , Toast.LENGTH_SHORT).show();
                    }
                    else Toast.makeText(RegisterActivity.this, R.string.passwordInvalid , Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(RegisterActivity.this, R.string.mailInvalid , Toast.LENGTH_SHORT).show();
            }
            else Toast.makeText(RegisterActivity.this, R.string.emptyField, Toast.LENGTH_SHORT).show();
        }
    };

    private View.OnClickListener radio_listener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.man:
                    gender = true;
                    break;
                case R.id.woman:
                    gender = false;
                    break;
            }
        }
    };

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
