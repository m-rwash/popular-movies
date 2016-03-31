package com.rwash.popularmovieapp;

/**
 * Created by bonzo on 3/31/16.
 */
public class ReviewObject {
    private String author;
    private String content;
    private String url;

    public ReviewObject(String author, String content, String url) {
        this.author = author;
        this.content = content;
        this.url = url;
    }

    @Override
    public String toString() {
        return "ReviewObject{" +
                "author='" + author + '\'' +
                ", content='" + content + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public String getAuthor() {
        return author;
    }
    public String getContent() {
        return content;
    }
    public String getUrl() {
        return url;
    }
}
