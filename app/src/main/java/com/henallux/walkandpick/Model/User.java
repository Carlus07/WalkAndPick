package com.henallux.walkandpick.Model;

import java.util.Date;

/**
 * Created by Max on 8/8/2017.
 */

public class User {
    private String email, lastName, firstName, locality, password, confirmPassword;
    private boolean gender;
   // private Date birthDate;

    public User(){}

    public User(String email, String lastName, String firstName, String locality, String password, boolean gender/*, Date birthDate*/){
        this.email = email;
        this.lastName = lastName;
        this.firstName = firstName;
        this.locality = locality;
        this.password = password;
        this.confirmPassword = password;
        this.gender = gender;
      //  this.birthDate = birthDate;
    }
}
