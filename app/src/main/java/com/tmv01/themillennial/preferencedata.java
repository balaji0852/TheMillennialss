package com.tmv01.themillennial;

import java.util.HashMap;

public class preferencedata {
    private   Boolean  Sports,Politics,Technology,Autonews,Tib,Entertainment,Fashion,Education;

    public preferencedata(){}

    public preferencedata(Boolean sports, Boolean politics, Boolean technology, Boolean autonews, Boolean tib, Boolean entertainment, Boolean fashion, Boolean education) {
        this.Sports = sports;
        this.Politics = politics;
        this.Technology = technology;
        this.Autonews = autonews;
        this.Tib = tib;
        this.Entertainment = entertainment;
        this.Fashion = fashion;
        this.Education = education;
    }

    public Boolean getSports() {
        return Sports;
    }

    public Boolean getPolitics() {
        return Politics;
    }

    public Boolean getTechnology() {
        return Technology;
    }

    public Boolean getAutonews() {
        return Autonews;
    }

    public Boolean getTib() {
        return Tib;
    }

    public Boolean getEntertainment() {
        return Entertainment;
    }

    public Boolean getFashion() {
        return Fashion;
    }

    public Boolean getEducation() {
        return Education;
    }
}