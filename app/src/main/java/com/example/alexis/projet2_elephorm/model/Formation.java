package com.example.alexis.projet2_elephorm.model;

import java.util.ArrayList;

/**
 * Created by Alexis on 10/08/2015.
 */
public class Formation {
    private String id, title, description;

    public Formation() {
    }

    public Formation(String id,String name, String description) {
        this.id = id;
        this.title = name;
        this.description = description;

    }

    public String getId() {  return id;  }

    public void setId(String id) { this.id = id; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
