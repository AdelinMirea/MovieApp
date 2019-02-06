package com.adelin.movieapp.utils;

public class MediaInfo {
    private String title;
    private String year;
    private String IMDBCode;
    private String posterLink;
    private String rating;
    private String plot;
    private String actors;
    private String gerne;

    public MediaInfo(String title, String year, String code, String posterLink){
        this.title = title;
        this.year = year;
        this.IMDBCode = code;
        this.posterLink = posterLink;
    }

    public MediaInfo(String title, String year, String IMDBCode, String posterLink, String rating, String plot, String actors, String gerne) {
        this.title = title;
        this.year = year;
        this.IMDBCode = IMDBCode;
        this.posterLink = posterLink;
        this.rating = rating;
        this.plot = plot;
        this.actors = actors;
        this.gerne = gerne;
    }

    @Override
    public String toString() {
        return title + '\n' +
                "Year: " + year + '\n';
    }

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getIMDBCode(){
        return IMDBCode;
    }

    public String getPosterLink() {
        return posterLink;
    }

    public String getRating() {
        return rating;
    }

    public String getPlot() {
        return plot;
    }

    public String getActors() {
        return actors;
    }

    public String getGerne() {
        return gerne;
    }
}
