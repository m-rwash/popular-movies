package com.rwash.popularmovieapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;

import com.rwash.popularmovieapp.fragments.MovieDetailFragment;
import com.rwash.popularmovieapp.fragments.MoviesGridFragment;
import com.rwash.popularmovieapp.model.Movie;


public class MainActivity extends AppCompatActivity implements MoviesGridFragment.GridFragmentCallbacks
{
    private final String LOG_TAG = MainActivity.class.getSimpleName();
    public static boolean twoPane=false;
    Toolbar toolbar;

    public final static String EXTRA_MOVIE_TITLE          = "MOVIE_TITLE";
    public final static String EXTRA_MOVIE_POSTER         = "MOVIE_POSTER";
    public final static String EXTRA_MOVIE_OVERVIEW       = "MOVIE_OVERVIEW";
    public final static String EXTRA_MOVIE_RELEASE_DATE   = "MOVIE_RELEASE_DATE";
    public final static String EXTRA_MOVIE_ORIGINAL_TITLE = "MOVIE_ORIGINAL_TITLE";
    public final static String EXTRA_MOVIE_ID             = "MOVIE_ID";

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(findViewById(R.id.movieDetailsContainer) != null)
        {
            // then it's tablet
            twoPane = true;
        }

    }

    public void setMovie(Movie movie)
    {
        if(twoPane)
        {
            // Its a tablet, so we'll start new fragment with movie's details
            // first we need to get instance of moveidetail fragment
            MovieDetailFragment movieDetailFragment = MovieDetailFragment.getInstance(movie);
            // then add fragment in the container
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.movieDetailsContainer, movieDetailFragment)
                    .commit();
        }
        else
        {
            // Its a phone, so we'll start new activity to display movie's details
            // first we need to start the detail activity
            Intent intent = new Intent(this, MovieDetailActivity.class);
            // then we'll pass movie info as extras with the intent
            intent.putExtra(EXTRA_MOVIE_TITLE,          movie.getTitle());
            intent.putExtra(EXTRA_MOVIE_POSTER,         movie.getImageUrl());
            intent.putExtra(EXTRA_MOVIE_OVERVIEW,       movie.getOverview());
            intent.putExtra(EXTRA_MOVIE_RELEASE_DATE,   movie.getReleaseDate());
            intent.putExtra(EXTRA_MOVIE_ORIGINAL_TITLE, movie.getOriginalTitle());
            intent.putExtra(EXTRA_MOVIE_ID,             movie.getMovieId());
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.action_settings)
        {
            // Start settings activity
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}