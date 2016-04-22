package com.rwash.popularmovieapp.model;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by bonzo on 3/26/16.
 */
public class Movie {

    /*Movie attributes*/
    private String title;
    private String imageUrl;
    private String overview;
    private String releaseDate;
    private String originalTitle;
    private String movieId;

    private String[] arrayInfo = {title, imageUrl, overview, releaseDate, originalTitle, movieId};

    private ArrayList<String> trailers = new ArrayList<>();
    private ArrayList<String> reviews  = new ArrayList<>();

    public Movie(String title, String imageUrl, String overview, String releaseDate, String originalTitle, String movieId)
    {
        this.title         = title;
        this.imageUrl      = imageUrl;
        this.overview      = overview;
        this.releaseDate   = releaseDate;
        this.originalTitle = originalTitle;
        this.movieId       = movieId;
    }

    /*Attributes setters*/
    public void setTitle(String title) {
        this.title = title;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }
    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    /*Attributes getters*/
    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getMovieId() {
        return movieId;
    }

    public String[] getArrayInfo(){
        return new String[]{title, imageUrl, overview, releaseDate, originalTitle, movieId};
    }

    public void setArrayInfo(String[] arrayInfo) {
        this.arrayInfo = Arrays.copyOf(arrayInfo, arrayInfo.length);
    }
}
