package com.rwash.popularmovieapp;

/**
 * Created by bonzo on 3/29/16.
 */
public class TrailerObject {

    private String key;
    private String name;

    public TrailerObject(String key, String name) {
        this.key = key;
        this.name = name;
    }

    @Override
    public String toString() {
        return "TrailerObject{" +
                "key='" + key + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
