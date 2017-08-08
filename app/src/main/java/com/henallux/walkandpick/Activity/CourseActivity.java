package com.henallux.walkandpick.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.henallux.walkandpick.R;
import com.henallux.walkandpick.Utility.CoursesAdapter;

public class CourseActivity extends AppCompatActivity {
    ListView ListView_Courses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        ListView_Courses = (ListView) findViewById(R.id.ListView_Courses);
        ShowCourses();
    }
    private void ShowCourses()
    {
        if(ListView_Courses == null) ListView_Courses = (ListView) findViewById(R.id.ListView_Courses);
        // Création et initialisation de l'Adapter pour les Listes
        CoursesAdapter adapter = new CoursesAdapter(CourseActivity.this, CurrentShoppingList.getProducts());
        // Initialisation de la liste avec les données
        ListView_Courses.setAdapter(adapter);
    }
}
