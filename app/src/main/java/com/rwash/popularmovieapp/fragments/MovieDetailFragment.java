package com.rwash.popularmovieapp.fragments;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.rwash.popularmovieapp.MovieDetailActivity;
import com.rwash.popularmovieapp.R;
import com.rwash.popularmovieapp.data.FavoritesDbHelper;
import com.rwash.popularmovieapp.model.Movie;
import com.rwash.popularmovieapp.model.Review;
import com.rwash.popularmovieapp.model.Trailer;
import com.rwash.popularmovieapp.views.adapters.ReviewAdapter;
import com.rwash.popularmovieapp.views.adapters.TrailerAdapter;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import com.rwash.popularmovieapp.data.FavoritesContract.FavoritesMoviesEntry;

/**
 * Created by bonzo on 3/25/16.
 */
public class MovieDetailFragment extends Fragment{

    public MovieDetailFragment(){}

    private Movie movie = null;

    private ArrayList<Trailer> trailers = new ArrayList<>();
    private ArrayList<Review> reviews = new ArrayList<>();

    private final String LOG_TAG = MovieDetailFragment.class.getSimpleName();

    private ListView trailersListView;
    private ListView reviewsListView;

    String[] args;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if(getArguments()!=null)    // tablet
        {
            args = Arrays.copyOf(getArguments().getStringArray("ArrayInfo"), 6);
            /*
            args[0] --> movieTitle
            args[1] --> moviePoster
            args[2] --> movieOverview
            args[3] --> movieReleaseDate
            args[4] --> movieOriginalTitle
            args[5] --> movieId
            */
            movie = new Movie(args[0], args[1], args[2], args[3], args[4], args[5]);
        }
        else    // phone
        {
            MovieDetailActivity activity = (MovieDetailActivity) getActivity();
            movie = activity.getMovie();
        }

        Log.v(LOG_TAG, "movie title: " + movie.getTitle());
        Log.v(LOG_TAG, "movie release date: " + movie.getReleaseDate());
    }
    @Override
    public void onStart() {
        super.onStart();
        new FetchTrailers().execute(movie.getMovieId());
        new FetchReviews().execute(movie.getMovieId());

    }

    /*this function is for returning moviedetail fragment
     instantiated with passed movie details*/
    public static MovieDetailFragment getInstance(Movie movie)
    {
        Bundle bundle = new Bundle();
        bundle.putStringArray("ArrayInfo", movie.getArrayInfo());
        MovieDetailFragment movieDetailFragment = new MovieDetailFragment();
        movieDetailFragment.setArguments(bundle);
        return  movieDetailFragment;
    }

    /*
    * Boolean Function to check if specific movie is in the database by it's ID  */
    public static boolean checkInDatabase(Context context, String movieId)
    {
        FavoritesDbHelper favoritesDbHelper = new FavoritesDbHelper(context);
        SQLiteDatabase db =  favoritesDbHelper.getWritableDatabase();

        String query = "SELECT * FROM " + FavoritesMoviesEntry.TABLE_NAME +
                       " WHERE " + FavoritesMoviesEntry.COLUMN_MOVIE_ID  + " = " + movieId;
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.getCount() <= 0)
        {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        TextView  movieTitleTextView = (TextView) rootView.findViewById(R.id.movieTitleTv);
        movieTitleTextView.setText(movie.getTitle());

        TextView movieOriginalTitleTextViw = (TextView) rootView.findViewById(R.id.movieOriginalTitleTv);
        movieOriginalTitleTextViw.setText("("+movie.getOriginalTitle()+")");

        TextView movieReleaseDateTextView = (TextView) rootView.findViewById(R.id.movieReleaseDateTv);
        movieReleaseDateTextView.setText(movie.getReleaseDate());

        TextView movieOverviewTextView = (TextView) rootView.findViewById(R.id.movieOverviewTv);
        movieOverviewTextView.setText(movie.getOverview());

        ImageView moviePosterImageView = (ImageView) rootView.findViewById(R.id.moviePosterIv);

        Picasso.with(getActivity())
                .load(movie.getImageUrl())
                .into(moviePosterImageView);

        trailersListView = (ListView)rootView.findViewById(R.id.trailersLv);
/*
        trailersListView.setOnTouchListener(new ListView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });*/

        reviewsListView = (ListView) rootView.findViewById(R.id.reviewsLv);

      /*  reviewsListView.setOnTouchListener(new ListView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });*/


        final ImageView favoriteButton = (ImageView) rootView.findViewById(R.id.favoriteButton);
        if(checkInDatabase(getActivity(), movie.getMovieId()))
            favoriteButton.setImageResource(R.drawable.star);

        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                FavoritesDbHelper favoritesDbHelper = new FavoritesDbHelper(getActivity());
                SQLiteDatabase db =  favoritesDbHelper.getWritableDatabase();

                if(checkInDatabase(getActivity(), movie.getMovieId()))
                {
                    db.delete(FavoritesMoviesEntry.TABLE_NAME, FavoritesMoviesEntry.COLUMN_MOVIE_ID + "=?", new String[]{movie.getMovieId()});
                    favoriteButton.setImageResource(R.drawable.unstar);
                    Toast.makeText(getActivity(), "Deleted from Favorites!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    ContentValues values = new ContentValues();
                    values.put(FavoritesMoviesEntry.COLUMN_MOVIE_ID, movie.getMovieId());
                    values.put(FavoritesMoviesEntry.COLUMN_MOVIE_TITLE, movie.getTitle());
                    values.put(FavoritesMoviesEntry.COLUMN_MOVIE_ORIGINAL_TITLE, movie.getOriginalTitle());
                    values.put(FavoritesMoviesEntry.COLUMN_MOVIE_RELEASE_DATE, movie.getReleaseDate());
                    values.put(FavoritesMoviesEntry.COLUMN_MOVIE_OVERVIEW, movie.getOverview());
                    values.put(FavoritesMoviesEntry.COLUMN_MOVIE_POSTER_URL, movie.getImageUrl());

                    db.insert(FavoritesMoviesEntry.TABLE_NAME, FavoritesMoviesEntry.COLUMN_MOVIE_TITLE, values);
                    favoriteButton.setImageResource(R.drawable.star);
                    Toast.makeText(getActivity(), "Added to Favorites!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rootView;
    }

    public class FetchReviews extends  AsyncTask<String, Void, ArrayList<Review>>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        private ArrayList<Review> parseJson(String jsonStr) throws JSONException
        {
            JSONObject jsonObject  = new JSONObject(jsonStr);
            JSONArray resultsArray = jsonObject.getJSONArray("results");

            ArrayList<Review> reviews = new ArrayList<Review>();

            for(int i=0; i<resultsArray.length(); i++)
            {
                JSONObject review = resultsArray.getJSONObject(i);
                String author  = review.getString("author");
                String content = review.getString("content");
                String url     = review.getString("url");

                reviews.add(new Review(author, content, url));
            }
            return reviews;
        }

        @Override
        protected ArrayList<Review> doInBackground(String... params)
        {
            // https://api.themoviedb.org/3/movie/157336/reviews?api_key=aeab5ec62e5555d42ace5362024cbbaf

            final String BASE_URL = "https://api.themoviedb.org/3/movie/";
            final String API_KEY  = "api_key";
            String ID = params[0];

            HttpURLConnection httpURLConnection;
            BufferedReader bufferedReader;

            String returnedJsonStr="";

            try
            {
                Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                        .appendPath(ID)
                        .appendPath("reviews")
                        .appendQueryParameter(API_KEY, MoviesGridFragment.api_key)
                        .build();
                URL url = new URL(builtUri.toString());

                Log.v(LOG_TAG, builtUri.toString());

                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();

                InputStream inputStream = httpURLConnection.getInputStream();
                StringBuffer stringBuffer = new StringBuffer();

                if(inputStream == null)
                    return null;

                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;

                while((line = bufferedReader.readLine())!=null)
                    stringBuffer.append(line);

                if(stringBuffer.length()==0)
                    return null;

                returnedJsonStr = stringBuffer.toString();

                Log.v(LOG_TAG, "FetchReviews Class: Returned JSON: "+returnedJsonStr);


            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
            try{
                return parseJson(returnedJsonStr);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Review> reviews) {
           if(!reviews.isEmpty())
           {
               MovieDetailFragment.this.reviews = reviews;

               for(Review review : MovieDetailFragment.this.reviews)
                   Log.v(LOG_TAG, review.toString());

               reviewsListView.setAdapter(new ReviewAdapter(getActivity(), MovieDetailFragment.this.reviews));
           }
        }
    }

    public class FetchTrailers extends AsyncTask<String, Void, ArrayList<Trailer>>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        private ArrayList<Trailer> parseJson(String jsonStr) throws JSONException
        {
            JSONObject jsonObject  = new JSONObject(jsonStr);
            JSONArray resultsArray = jsonObject.getJSONArray("results");

            ArrayList<Trailer> trailers = new ArrayList<Trailer>();

            for(int i=0; i<resultsArray.length(); i++)
            {
                JSONObject trailer = resultsArray.getJSONObject(i);
                String key  = trailer.getString("key");
                String name = trailer.getString("name");

                trailers.add(new Trailer(key, name));
            }

            return trailers;
        }
        @Override
        protected ArrayList<Trailer> doInBackground(String... params) {

            // https://api.themoviedb.org/3/movie/278/videos?api_key=aeab5ec62e5555d42ace5362024cbbaf

            final String BASE_URL = "https://api.themoviedb.org/3/movie/";
            final String API_KEY  = "api_key";
            String ID = params[0];

            HttpURLConnection httpURLConnection;
            BufferedReader bufferedReader;

            String returnedJsonStr="";

            try
            {
                Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                        .appendPath(ID)
                        .appendPath("videos")
                        .appendQueryParameter(API_KEY, MoviesGridFragment.api_key)
                        .build();
                URL url = new URL(builtUri.toString());

                Log.v(LOG_TAG, builtUri.toString());

                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();

                InputStream inputStream = httpURLConnection.getInputStream();
                StringBuffer stringBuffer = new StringBuffer();

                if(inputStream == null)
                    return null;

                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;

                while((line = bufferedReader.readLine())!=null)
                    stringBuffer.append(line);

                if(stringBuffer.length()==0)
                    return null;

                returnedJsonStr = stringBuffer.toString();

                Log.v(LOG_TAG, "FetchTrailers Class: Returned JSON: "+returnedJsonStr);


            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
            try{
                return parseJson(returnedJsonStr);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(ArrayList<Trailer> trailers) {
            if(!trailers.isEmpty())
            {
                MovieDetailFragment.this.trailers = trailers;

                for(Trailer trailer : MovieDetailFragment.this.trailers)
                    Log.v(LOG_TAG, trailer.toString());

                trailersListView.setAdapter(new TrailerAdapter(getActivity(), MovieDetailFragment.this.trailers));
            }
        }
    }
}
