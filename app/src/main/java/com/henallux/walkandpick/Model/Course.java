package com.henallux.walkandpick.Model;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Max on 8/8/2017.
 */

public class Course {
    private int id, difficulty;
    private String name, mapLink;
    private double mileage;
    private Collection<Point> points;

    public Course(){}

    public Course(int id, String name, double mileage, int difficulty, String mapLink, Collection<Point> points){
        this.id = id;
        this.name = name;
        this.mileage = mileage;
        this.difficulty = difficulty;
        this.mapLink = mapLink;
        this.points = points;
    }

    public String getMapLink() {
        return mapLink;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMileage() {
        return mileage;

    }

    public void setMileage(double mileage) {
        this.mileage = mileage;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public void setMapLink(String mapLink) {
        this.mapLink = mapLink;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
