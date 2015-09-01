package com.example.alexis.projet2_elephorm.model;

/**
 * Created by Alexis on 27/08/2015.
 */
public class Video {
    private String id, title, field_vignette,field_video;
    private int duration;

    public Video() {
    }

    public Video(String id, String name, String field_vignette,
                     Integer duration,
                     String field_video) {
        this.id = id;
        this.title = name;
        this.field_vignette = field_vignette;
        this.duration = duration;
        this.field_video = field_video;

    }

    public String getId() {  return id;  }

    public void setId(String id) { this.id = id; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getField_vignette() {
        return field_vignette;
    }

    public void setField_vignette(String field_vignette) {
        this.field_vignette = field_vignette;
    }

    public int getDuration() {  return duration;  }

    public void setDuration(Integer duration) { this.duration = duration; }

    public String getField_video() {  return field_video;  }

    public void setField_video(String field_video) { this.field_video = field_video; }

}

