package com.henallux.walkandpick.Model;

/**
 * Created by Max on 8/8/2017.
 */

public class Point {
    private int id, order;
    private Place place;
    private Course course;

    public Point(){}

    public Point(int id, int order, Place place, Course course){
        this.id = id;
        this.order = order;
        this.place = place;
        this.course = course;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
