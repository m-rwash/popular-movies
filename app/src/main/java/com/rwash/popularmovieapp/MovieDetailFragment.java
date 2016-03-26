package com.rwash.popularmovieapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by bonzo on 3/25/16.
 */
public class MovieDetailFragment extends Fragment{

    private String movieTitle       = null;
    private String moviePoster      = null;
    private String movieOverview    = null;
    private String movieReleaseDate = null;

    private final String LOG_TAG = MovieDetailFragment.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        MovieDetailActivity activity = (MovieDetailActivity) getActivity();
        movieTitle       = activity.getMovieTitle();
        moviePoster      = activity.getMoviePoster();
        movieOverview    = activity.getMovieOverview();
        movieReleaseDate = activity.getMovieReleaseDate();

        Log.v(LOG_TAG, "movie title: "+movieTitle);
        Log.v(LOG_TAG, "movie release date: "+movieReleaseDate);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        TextView  movieTitleTextView = (TextView) rootView.findViewById(R.id.movieTitleTv);
        movieTitleTextView.setText(movieTitle);

        TextView movieReleaseDateTextView = (TextView) rootView.findViewById(R.id.movieReleaseDateTv);
        movieReleaseDateTextView.setText(movieReleaseDate);

        TextView movieOverviewTextView = (TextView) rootView.findViewById(R.id.movieOverviewTv);
        movieOverviewTextView.setText(movieOverview);

        ImageView moviePosterImageView = (ImageView) rootView.findViewById(R.id.moviePosterIv);

        Picasso.with(getActivity())
                .load(moviePoster)
                .into(moviePosterImageView);

        return rootView;
    }
}
