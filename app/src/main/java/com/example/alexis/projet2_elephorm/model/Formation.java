package com.example.alexis.projet2_elephorm.model;

/**
 * Created by Alexis on 13/08/2015.
 */
public class Formation {
    private String id, title,subtitle, description,product_url,teaser_text,teaser,qcm,objectives,ean13,publishedDate,poster,items;
    private int price,duration;

    public Formation() {
    }

    public Formation(String id, String name, String description, String subtitle,
                     String product_url, String ean13, Integer price, Integer duration,
                     String objectives, String qcm, String teaser, String teaser_text,
                     String publishedDate, String poster, String items) {
        this.id = id;
        this.title = name;
        this.subtitle = subtitle;
        this.product_url = product_url;
        this.ean13 = ean13;
        this.price = price;
        this.description = description;
        this.duration = duration;
        this.objectives = objectives;
        this.qcm = qcm;
        this.teaser_text = teaser_text;
        this.teaser = teaser;
        this.publishedDate = publishedDate;
        this.poster = poster;
        this.items = items;
        // reste image category et subcat

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

    public String getSubtitle() {  return subtitle;  }

    public void setSubtitle(String subtitle) { this.subtitle = subtitle; }

    public String getProduct_url() {  return product_url;  }

    public void setProduct_url(String product_url) { this.product_url = product_url; }

    public String getEan13() {  return ean13;  }

    public void setEan13(String ean13) { this.ean13 = ean13; }

    public int getPrice() {  return price;  }

    public void setPrice(Integer price) { this.price = price; }

    public int getDuration() {  return duration;  }

    public void setDuration(Integer duration) { this.duration = duration; }

    public String getQcm() {  return qcm;  }

    public void setQcm(String qcm) { this.qcm = qcm; }

    public String getTeaser_text() {  return teaser_text;  }

    public void setTeaser_text(String teaser_text) { this.teaser_text = teaser_text; }

    public String getTeaser() {  return teaser;  }

    public void setTeaser(String teaser) { this.teaser = teaser; }

    public String getPublishedDate() {  return publishedDate;  }

    public void setPublishedDate(String publishedDate) { this.publishedDate = publishedDate; }

    public String getPoster() {  return poster;  }

    public void setPoster(String poster) { this.poster = poster; }

    public String getObjectives() {  return objectives;  }

    public void setObjectives(String objectives) { this.objectives = objectives; }

    public String getItems() {  return items;  }

    public void setItems(String items) { this.items = items; }

}
