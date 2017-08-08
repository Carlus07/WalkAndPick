package com.henallux.walkandpick.Utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

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
    public Object getItem(int position) {
        return courseList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return courseList.indexOf(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
