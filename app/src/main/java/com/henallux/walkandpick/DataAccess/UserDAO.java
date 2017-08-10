package com.henallux.walkandpick.DataAccess;

import android.util.JsonReader;

import com.google.gson.Gson;
import com.henallux.walkandpick.Model.User;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public class UserDAO {

    public String Connection (String mail, String password) throws Exception{
        int responseCode = 0;
        String token = null;
        try{
            URL url = new URL("http://walkandpickwebapp20170727042830.azurewebsites.net/token");
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
            }
            writer.close();
            outputStream.close();
            connection.disconnect();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return token;
    }

    public User Register (User user){
        int responseCode = 0;
        try{
            URL url = new URL("http://walkandpickwebapp20170727042830.azurewebsites.net/api/Account/Register");
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

            responseCode = connection.getResponseCode();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return user;
    }
}
