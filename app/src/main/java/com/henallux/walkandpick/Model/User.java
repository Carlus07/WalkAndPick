package com.henallux.walkandpick.Model;

/**
 * Created by Max on 8/8/2017.
 */

public class User {
    private String email, lastName, firstName, locality, password, confirmPassword;
    private boolean gender;

    public User(){}

    public User(String email, String lastName, String firstName, String locality, String password, boolean gender){
        this.email = email;
        this.lastName = lastName;
        this.firstName = firstName;
        this.locality = locality;
        this.password = password;
        this.confirmPassword = password;
        this.gender = gender;
    }
}
