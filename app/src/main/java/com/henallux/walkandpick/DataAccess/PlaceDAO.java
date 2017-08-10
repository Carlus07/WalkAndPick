package com.henallux.walkandpick.DataAccess;

import com.henallux.walkandpick.Model.Place;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class PlaceDAO {

    public ArrayList<Place> getAllPlacesFromTheCourse(int courseId, String token){
        int responseCode = 0;
        ArrayList<Place> places = new ArrayList<>();
        try{
            URL url = new URL("http://walkandpickwebapp20170727042830.azurewebsites.net/api/Places/placesByIdCourse/"+courseId);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-type","application/json");
            connection.setRequestProperty("Authorization", "Bearer "+token);

            connection.connect();

            responseCode = connection.getResponseCode();
            if (responseCode == 200){
                InputStream inputStream;
                inputStream = connection.getInputStream();

                InputStreamReader inputStreamReader;
                inputStreamReader = new InputStreamReader(inputStream);

                BufferedReader br = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String stringJSON = "",line;
                while((line=br.readLine())!=null){
                    sb.append(line);
                }
                br.close();
                stringJSON = sb.toString();

                //Json to places
                JSONArray jsonArray = new JSONArray((stringJSON));
                for (int i=0; i<jsonArray.length();i++){
                    JSONObject jsonPlace = jsonArray.getJSONObject(i);
                    int id = Integer.parseInt(jsonPlace.getString("ID"));
                    Place place = new Place(
                            id,
                            jsonPlace.getString("Name"),
                            jsonPlace.getString("Description"),
                            jsonPlace.getString("GPSAdress")
                            ,jsonPlace.getString("Picture"));
                    places.add(place);
                }
            }
            connection.disconnect();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return places;
    }
}
