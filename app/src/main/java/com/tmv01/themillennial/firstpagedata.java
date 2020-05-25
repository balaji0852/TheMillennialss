package com.tmv01.themillennial;

import android.media.Image;

public class firstpagedata {
    String aid,category,date,headline,poname,image,textualdata;
    Integer Views,likes;



    public firstpagedata(String aid, String category, String date, String headline, String poname, String image, String textualdata,
                         Integer views,Integer likes){
        this.aid = aid;
        this.category = category;
        this.date = date;
        this.headline = headline;
        this.poname = poname;
        this.image = image;
        this.textualdata = textualdata;
        this.Views = views;
        this.likes = likes;

    }

    public firstpagedata(){

    }

    public Integer getLikes() {
        return likes;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getPoname() {
        return poname;
    }

    public void setPoname(String poname) {
        this.poname = poname;
    }

    public String getTextualdata() {
        return textualdata;
    }

    public void setTextualdata(String textualdata) {
        this.textualdata = textualdata;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getViews() {
        return Views;
    }

    public void setViews(Integer views) {
        Views = views;
    }
}
