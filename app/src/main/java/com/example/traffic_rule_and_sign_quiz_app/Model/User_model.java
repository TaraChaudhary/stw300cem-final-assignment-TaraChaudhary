package com.example.traffic_rule_and_sign_quiz_app.Model;

import android.widget.TextView;

public class User_model {

    String _id;
    String firstname;
    String lastname;
    String phone;
    String gender;
    String dob;
    String email;
    String username;
    String password;
    String image;
    String token;

    public User_model(String _id, String firstname, String lastname, String phone, String gender, String dob, String email, String username, String password, String image, String token) {
        this._id = _id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
        this.gender = gender;
        this.dob = dob;
        this.email = email;
        this.username = username;
        this.password = password;
        this.image = image;
        this.token = token;
    }

    public User_model(String firstname, String lastname, String phone, String email, String username, String image) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
        this.email = email;
        this.username = username;
        this.image = image;
    }

    public User_model(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String get_id() {
        return _id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getPhone() {
        return phone;
    }

    public String getGender() {
        return gender;
    }

    public String getDob() {
        return dob;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getImage() {
        return image;
    }

    public String getToken() {
        return token;
    }
}
