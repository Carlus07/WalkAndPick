package com.henallux.walkandpick.Utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.henallux.walkandpick.Model.Course;
import com.henallux.walkandpick.R;

import java.util.ArrayList;

public class CoursesAdapter extends BaseAdapter {
    //Conteneur de notre Liste d'éléments
    private ArrayList<Course> courseList;

    public ArrayList<Course> Courses(){
        return  courseList;
    }

    //Contexte dans lequel est présent notre adapter
    private Context context;

    //Mécanisme pour gérer l'affichage graphique depuis un layout XML
    private LayoutInflater inflater;

    public CoursesAdapter(Context context, ArrayList<Course> courseList) {
        this.context = context;
        this.courseList = courseList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return courseList.size();
    }

    @Override
    public Course getItem(int position) {
        return courseList.get(position);
    }

    public long getItemId(int position) {
        return courseList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LinearLayout layoutItem;

        //(1) : Réutilisation des layouts
        if (convertView == null)
        {
            //Initialisation de notre item à partir du  layout XML "course_holder"
            layoutItem = (LinearLayout) inflater.inflate(R.layout.course_holder, parent, false);
        }
        else layoutItem = (LinearLayout) convertView;

        //(2) : Récupération des TextView de notre layout
        final TextView courseName = (TextView)layoutItem.findViewById(R.id.courseName);
        final TextView courseInfo = (TextView)layoutItem.findViewById(R.id.courseInfo);

        //(3) : Renseignement des valeurs
        Course c = getItem(position);
        courseName.setText(c.getName());
        courseInfo.setText(context.getResources().getString(R.string.distance) + " : " + c.getMileage() + "  -  " + context.getResources().getString(R.string.difficulty) + " : " + c.getDifficulty());

        //On retourne l'item créé.
        return layoutItem;
    }
}
