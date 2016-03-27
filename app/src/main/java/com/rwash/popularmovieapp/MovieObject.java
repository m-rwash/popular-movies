package com.rwash.popularmovieapp;

/**
 * Created by bonzo on 3/26/16.
 */
public class MovieObject {

    /*Movie attributes*/
    private String title;
    private String imageUrl;
    private String overview;
    private String releaseDate;
    private String originalTitle;

    public MovieObject(String title, String imageUrl, String overview, String releaseDate, String originalTitle) {
        this.title         = title;
        this.imageUrl      = imageUrl;
        this.overview      = overview;
        this.releaseDate   = releaseDate;
        this.originalTitle = originalTitle;
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
}
