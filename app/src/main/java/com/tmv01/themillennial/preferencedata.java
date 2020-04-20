package com.tmv01.themillennial;

public class preferencedata {
    Boolean AutoNews, Education, Entertainment, Fashion, Politics, Sports, Technology, tib;

    public preferencedata() {

    }

    public preferencedata(Boolean autoNews, Boolean education, Boolean entertainment, Boolean fashion, Boolean politics, Boolean sports, Boolean technology, Boolean tib) {
        AutoNews = autoNews;
        Education = education;
        Entertainment = entertainment;
        Fashion = fashion;
        Politics = politics;
        Sports = sports;
        Technology = technology;
        this.tib = tib;
    }

    public Boolean getAutoNews() {
        return AutoNews;
    }

    public Boolean getEducation() {
        return Education;
    }

    public Boolean getEntertainment() {
        return Entertainment;
    }

    public Boolean getFashion() {
        return Fashion;
    }

    public Boolean getPolitics() {
        return Politics;
    }

    public Boolean getSports() {
        return Sports;
    }

    public Boolean getTechnology() {
        return Technology;
    }

    public Boolean getTib() {
        return tib;
    }
}