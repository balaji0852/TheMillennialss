package com.tmv01.themillennial;

import android.media.Image;

public class firstpagedata {
    String aid,category,date,headline,poname,textualdata,image;

    public firstpagedata(String aid, String category, String date, String headline, String poname, String textualdata, String image) {
        this.aid = aid;
        this.category = category;
        this.date = date;
        this.headline = headline;
        this.poname = poname;
        this.textualdata = textualdata;
        this.image= image;
    }

    public firstpagedata(){

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
}
