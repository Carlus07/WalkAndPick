package com.henallux.walkandpick.Activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.henallux.walkandpick.Application;
import com.henallux.walkandpick.DataAccess.CourseDAO;
import com.henallux.walkandpick.Model.Course;

import java.util.ArrayList;

/**
 * Created by Max on 8/8/2017.
 */

public class CourseActivity extends AppCompatActivity {
    private ListView courseList;
    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //...
        new CourseActivity.LoadCourses().execute();
    }


    private class LoadCourses extends AsyncTask<Void, Void, ArrayList<Course>>
    {
        @Override
        protected ArrayList<Course> doInBackground(Void...params){
            Application app = (Application) getApplicationContext();
            String token = app.getToken();
            CourseDAO courseDAO = new CourseDAO();
            ArrayList<Course> courses = new ArrayList<>();
            try{
                courses = courseDAO.getAllCourses(token);
            }
            catch (Exception e){
                e.printStackTrace();
            }
            return courses;
        }

        @Override
        protected void onPostExecute(ArrayList<Course> courses){
            //ArrayAdapter<Course> adapter = new ArrayAdapter<Course>(this, /*Layout de courses*/, courses.toString());
            //courseList.setAdapter(adapter);
        }
    }

}
