package com.example.aidm_movies;

public class Movie {
    private String title;
    private String year;
    private String imdbID;

    public Movie(String name, String year, String imdbID){
        this.title = name;
        this.year = year;
        this.imdbID = imdbID;
    }

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getImdbID() {
        return imdbID;
    }
}
