package com.henallux.walkandpick.DataAccess;

import android.content.Context;

import com.henallux.walkandpick.Application;
import com.henallux.walkandpick.Model.Course;
import com.henallux.walkandpick.Model.Search;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class CourseDAO {
    private int response;

    public ArrayList<Course> getAllCourses(String token){
        ArrayList<Course> courses = new ArrayList<>();
        int responseCode = 0;

        try{
            URL url = new URL("http://walkandpickwebapp20170727042830.azurewebsites.net/api/Courses/");
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
                JSONArray jsonArray = new JSONArray((stringJSON));
                for (int i=0; i<jsonArray.length();i++){
                    JSONObject jsonCourse = jsonArray.getJSONObject(i);
                    Course course = new Course(
                            jsonCourse.getInt("ID"),
                            jsonCourse.getString("Name"),
                            jsonCourse.getDouble("Mileage"),
                            jsonCourse.getInt("Difficulty"),
                            jsonCourse.getString("MapLink"));
                    courses.add(course);
                }
                response = 0;
            }
            else response = responseCode;
            connection.disconnect();
        }
        catch (Exception e){
            response = 1;
        }
        return courses;
    }
    public ArrayList<Course> getCoursesWithParameter(Search search, String token){
        ArrayList<Course> courses = new ArrayList<>();
        int responseCode = 0;

        try{

            URL url = new URL("http://walkandpickwebapp20170727042830.azurewebsites.net/api/courses/search/"+search.getDifficulty() + "/" + search.getKilometer() + "/" + search.getIdPlace());
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
                JSONArray jsonArray = new JSONArray((stringJSON));
                for (int i=0; i<jsonArray.length();i++){
                    JSONObject jsonCourse = jsonArray.getJSONObject(i);
                    Course course = new Course(
                            jsonCourse.getInt("ID"),
                            jsonCourse.getString("Name"),
                            jsonCourse.getDouble("Mileage"),
                            jsonCourse.getInt("Difficulty"),
                            jsonCourse.getString("MapLink"));
                    courses.add(course);
                }
                response = 0;
            }
            else response = responseCode;
            connection.disconnect();
        }
        catch (Exception e){
            response = 1;
        }
        return courses;
    }
    public int getError()
    {
        return response;
    }
}
