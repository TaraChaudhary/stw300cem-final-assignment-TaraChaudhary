package com.example.traffic_rule_and_sign_quiz_app.Model;

import android.widget.EditText;

public class Signal {

    String name;
    String description;
    String image;

    public Signal(String name, String description, String image) {
        this.name = name;
        this.description = description;
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }
}
