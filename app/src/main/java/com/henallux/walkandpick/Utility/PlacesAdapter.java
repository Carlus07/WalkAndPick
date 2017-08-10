package com.henallux.walkandpick.Utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.henallux.walkandpick.Model.Place;
import com.henallux.walkandpick.R;

import java.util.ArrayList;

public class PlacesAdapter extends BaseAdapter {
    //Conteneur de notre Liste d'éléments
    private ArrayList<Place> placeList;

    public ArrayList<Place> Places(){
        return  placeList;
    }

    //Contexte dans lequel est présent notre adapter
    private Context context;

    //Mécanisme pour gérer l'affichage graphique depuis un layout XML
    private LayoutInflater inflater;

    public PlacesAdapter(Context context, ArrayList<Place> courseList) {
        this.context = context;
        this.placeList = courseList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return placeList.size();
    }

    @Override
    public Place getItem(int position) {
        return placeList.get(position);
    }

    public long getItemId(int position) {
        return placeList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LinearLayout layoutItem;

        //(1) : Réutilisation des layouts
        if (convertView == null)
        {
            //Initialisation de notre item à partir du  layout XML "place_holder"
            layoutItem = (LinearLayout) inflater.inflate(R.layout.place_holder, parent, false);
        }
        else layoutItem = (LinearLayout) convertView;

        //(2) : Récupération des TextView de notre layout
        final TextView courseName = (TextView)layoutItem.findViewById(R.id.placeName);

        //(3) : Renseignement des valeurs
        Place p = getItem(position);
        courseName.setText(p.getName());

        //On retourne l'item créé.
        return layoutItem;
    }
}
