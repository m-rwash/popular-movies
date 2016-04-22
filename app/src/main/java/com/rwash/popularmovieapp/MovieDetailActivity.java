package com.rwash.popularmovieapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.rwash.popularmovieapp.fragments.MovieDetailFragment;
import com.rwash.popularmovieapp.model.Movie;

public class MovieDetailActivity extends AppCompatActivity
{
    private String movieTitle         = null;
    private String moviePoster        = null;
    private String movieOverview      = null;
    private String movieReleaseDate   = null;
    private String movieOriginalTitle = null;
    private String movieId            = null;

    private Movie movie = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        if(savedInstanceState==null)
        {
            Bundle extras = getIntent().getExtras();

            if(extras!=null)
            {
                movieTitle         = extras.getString("MOVIE_TITLE");
                moviePoster        = extras.getString("MOVIE_POSTER");
                movieOverview      = extras.getString("MOVIE_OVERVIEW");
                movieReleaseDate   = extras.getString("MOVIE_RELEASE_DATE");
                movieOriginalTitle = extras.getString("MOVIE_ORIGINAL_TITLE");
                movieId            = extras.getString("MOVIE_ID");

                movie = new Movie(movieTitle, moviePoster, movieOverview, movieReleaseDate, movieOriginalTitle, movieId);

//                Log.v("moviedetailactivity", "TEST: "+extras.getString("MOVIE_TILE"));
//                Log.v("moviedetailactivity", "TEST2: " + movieReleaseDate);

                getSupportFragmentManager().beginTransaction()
                        .add(R.id.movie_detail_container,new MovieDetailFragment()).commit();
            }

        }
    }

    public Movie getMovie()
    {
        return movie;
    }
    
}
