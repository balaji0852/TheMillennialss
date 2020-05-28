package com.tmv01.themillennial;

public class likenviews {

        Number views,likes;
        String textualdata;
        public likenviews(){

        }



    public likenviews(Number views, Number likes, String textualdata) {
        this.views = views;
        this.likes = likes;
        this.textualdata = textualdata;
    }

    public String getTextualdata() {
        return textualdata;
    }

    public Number getLikes() {
            return likes;
        }

        public Number getViews() {
            return views;
        }
    }


