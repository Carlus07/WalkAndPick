package com.henallux.walkandpick.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.henallux.walkandpick.Application;
import com.henallux.walkandpick.DataAccess.CourseDAO;
import com.henallux.walkandpick.Model.Course;
import com.henallux.walkandpick.R;
import com.henallux.walkandpick.Utility.CoursesAdapter;

import java.util.ArrayList;

public class CourseActivity extends AppCompatActivity {
    ListView ListView_Courses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        ListView_Courses = (ListView) findViewById(R.id.listItem);
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

            // Création et initialisation de l'Adapter pour les Listes
            CoursesAdapter adapter = new CoursesAdapter(CourseActivity.this, courses);
            // Initialisation de la liste avec les données
            ListView_Courses.setAdapter(adapter);

            ListView_Courses.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    Intent intent = new Intent(CourseActivity.this, PlaceActivity.class);
                    intent.putExtra("idCourse", (int) id);
                    startActivity(intent);
                }
            });
        }
    }
}
