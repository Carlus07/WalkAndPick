package com.henallux.walkandpick.DataAccess;

import android.content.Context;
import android.util.JsonReader;
import android.widget.Toast;

import com.google.gson.Gson;
import com.henallux.walkandpick.Application;
import com.henallux.walkandpick.Model.User;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public class UserDAO {
    private int response;

    public String Connection (String mail, String password) throws Exception{
        int responseCode = 0;
        String token = null;
        try{
            URL url = new URL(Application.getUrl() +"/token");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-type"," x-www-form-urlencoded");
            connection.setDoOutput(true);
            OutputStream outputStream = connection.getOutputStream();
            OutputStreamWriter writer = new OutputStreamWriter(outputStream);
            connection.connect();
            writer.write("Username="+mail+"&Password="+password+"&grant_type=password");
            writer.flush();
            responseCode = connection.getResponseCode();
            if (responseCode == 200){
                InputStream inputStream;
                inputStream = connection.getInputStream();

                InputStreamReader inputStreamReader;
                inputStreamReader = new InputStreamReader(inputStream);

                JsonReader JSON = new JsonReader(inputStreamReader);
                JSON.beginObject();

                JSON.nextName();
                token = JSON.nextString();
                response = 0;
            }
            else response = responseCode;
            writer.close();
            outputStream.close();
            connection.disconnect();
        }
        catch (Exception e){
            response = 1;
        }
        return token;
    }

    public void Register (User user){
        int responseCode = 0;
        try{
            URL url = new URL(Application.getStringApi()+"Account/Register");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-type","application/json");
            connection.setDoOutput(true);

            Gson gson = new Gson();
            String json = gson.toJson(user);
            OutputStream outputStream = connection.getOutputStream();
            OutputStreamWriter writer = new OutputStreamWriter(outputStream);
            writer.write(json);
            writer.flush();

            connection.connect();

            response = (connection.getResponseCode() != 200) ? connection.getResponseCode() : 0;
        }
        catch(Exception e){
            response = 1;
        }
    }
    public int getError()
    {
        return response;
    }
}
