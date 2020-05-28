package com.tmv01.themillennial;

public class savedpagedata {
    String image,headline,date,category;
    public savedpagedata(){

    }

    public savedpagedata(String image, String headline, String date, String category) {
        this.image = image;
        this.headline = headline;
        this.date = date;
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }

    public String getImage() {
        return image;
    }

    public String getHeadline() {
        return headline;
    }
}
