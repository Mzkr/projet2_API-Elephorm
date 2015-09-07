package com.example.alexis.projet2_elephorm.model;

/**
 * Created by Alexis on 07/09/2015.
 */
public class Historique {
    private String id, title,listSubCat;

    public Historique() {
    }

    public Historique(String id, String title, String listSubCat) {
        this.id = id;
        this.title = title;
        this.listSubCat = listSubCat;



    }

    public String getId() {  return id;  }

    public void setId(String id) { this.id = id; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getListSubCat() {
        return listSubCat;
    }

    public void setListSubCat(String listSubCat) {
        this.listSubCat = listSubCat;
    }


}
