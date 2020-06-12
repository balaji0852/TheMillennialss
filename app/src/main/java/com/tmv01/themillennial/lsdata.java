package com.tmv01.themillennial;

public class lsdata {
      String image,headline,category,date;
      Boolean saved,liked;
      public  lsdata()
      {

      }
      public lsdata(String image, String headline, String category, String date, Boolean saved, Boolean liked) {
            this.image = image;
            this.headline = headline;
            this.category = category;
            this.date = date;
            this.saved = saved;
            this.liked = liked;
      }

      public String getImage() {
            return image;
      }

      public String getHeadline() {
            return headline;
      }

      public String getCategory() {
            return category;
      }

      public String getDate() {
            return date;
      }

      public Boolean getSaved() {
            return saved;
      }

      public Boolean getLiked() {
            return liked;
      }
}
