package com.henallux.walkandpick.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class Place implements Serializable {
    private int id;
    private String name, description, gpsAdress, picture;
    private ArrayList<Point> points;

    public Place(){}

    public Place(int id, String name, String description, String gpsAdress, String picture/*, ArrayList<Point> points*/) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.gpsAdress = gpsAdress;
        this.picture = picture;
      //  this.points = points;
    }
    public String toString()
    {
        return name;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGpsAdress() {
        return gpsAdress;
    }

    public void setGpsAdress(String gpsAdress) {
        this.gpsAdress = gpsAdress;
    }

    public String getPicture()
    {
        String [] pictures = picture.split(".j");
        return pictures[0];
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
