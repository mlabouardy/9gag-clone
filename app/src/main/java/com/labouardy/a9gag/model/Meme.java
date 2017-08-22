package com.labouardy.a9gag.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mohamed on 22/08/2017.
 */

public class Meme {
    @SerializedName("Description")
    private String description;

    @SerializedName("Image")
    private String image;

    public Meme(){}

    public Meme(String description, String image){
        this.description = description;
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return description + " " + image;
    }
}
