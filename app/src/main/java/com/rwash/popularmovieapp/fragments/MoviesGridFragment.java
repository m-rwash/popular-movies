package com.rwash.popularmovieapp.fragments;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rwash.popularmovieapp.data.FavoritesContract.FavoritesMoviesEntry;
import com.rwash.popularmovieapp.data.FavoritesDbHelper;
import com.rwash.popularmovieapp.views.adapters.MoviesAdapter;
import com.rwash.popularmovieapp.R;
import com.rwash.popularmovieapp.model.Movie;

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

public class MoviesGridFragment extends Fragment
{

    private RecyclerView recyclerViewMovies;
    public final static String api_key = "aeab5ec62e5555d42ace5362024cbbaf";

    public MoviesGridFragment()
    {
        // Required empty public constructor
    }

    public void update()
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String order = sharedPreferences.getString(getString(R.string.pref_order_key),
                                                   getString(R.string.pref_order_popular));

        if(order.equals("favorites"))
        {
            ArrayList<Movie> movies = getMoviesFromDatabase();
            recyclerViewMovies.setAdapter(new MoviesAdapter(movies));
        }
        else
        {
            new FetchMovies().execute(order);
        }
    }

    @Override
    public void onStart()
    {
        super.onStart();
        update();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_movies_grid, container, false);

        recyclerViewMovies = (RecyclerView) rootView.findViewById(R.id.rVmovies);
        recyclerViewMovies.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerViewMovies.setHasFixedSize(true);

        return rootView;
    }


    public ArrayList<Movie> getMoviesFromDatabase()
    {
        ArrayList<Movie> movies = new ArrayList<Movie>();

        FavoritesDbHelper favoritesDbHelper = new FavoritesDbHelper(getActivity());
        SQLiteDatabase db =  favoritesDbHelper.getWritableDatabase();

        String[] columns = {
                FavoritesMoviesEntry.COLUMN_MOVIE_ID,
                FavoritesMoviesEntry.COLUMN_MOVIE_TITLE,
                FavoritesMoviesEntry.COLUMN_MOVIE_ORIGINAL_TITLE,
                FavoritesMoviesEntry.COLUMN_MOVIE_RELEASE_DATE,
                FavoritesMoviesEntry.COLUMN_MOVIE_OVERVIEW,
                FavoritesMoviesEntry.COLUMN_MOVIE_POSTER_URL
        };

        Cursor cursor = db.query(
                FavoritesMoviesEntry.TABLE_NAME,    // table name
                columns,                            // columns to return
                null,
                null,
                null,
                null,
                null
        );

        /* retrieve the database by cursor. And construct movie objects from each row
           each row representing movie object
           add those movies(rows) up to ArrayList of movies*/
        if(cursor != null && cursor.moveToFirst())
        {
            do{
                String movieID            = cursor.getInt(cursor.getColumnIndex(FavoritesMoviesEntry.COLUMN_MOVIE_ID))+"";
                String movieTitle         = cursor.getString(cursor.getColumnIndex(FavoritesMoviesEntry.COLUMN_MOVIE_TITLE));
                String movieOriginalTitle = cursor.getString(cursor.getColumnIndex(FavoritesMoviesEntry.COLUMN_MOVIE_ORIGINAL_TITLE));
                String movieReleaseDate   = cursor.getString(cursor.getColumnIndex(FavoritesMoviesEntry.COLUMN_MOVIE_RELEASE_DATE));
                String movieOverview      = cursor.getString(cursor.getColumnIndex(FavoritesMoviesEntry.COLUMN_MOVIE_OVERVIEW));
                String moviePosterUrl     = cursor.getString(cursor.getColumnIndex(FavoritesMoviesEntry.COLUMN_MOVIE_POSTER_URL));
                Movie movie = new Movie(movieTitle, moviePosterUrl, movieOverview, movieReleaseDate, movieOriginalTitle, movieID);
                movies.add(movie);
                Log.v("getMovieFromDatabase", movie.toString());
            } while (cursor.moveToNext());
        }

        return movies;
    }

    /* Fetching Movies data from TMBD api in background thread by extending AsyncTask class */
    public class FetchMovies extends AsyncTask<String, Void, ArrayList<Movie>>
    {
        private final String LOG_TAG = "FetchMovies Class";

        private String[] imagePaths;
        private String[] imageUrls;

        /*Base image url*/
        private final String imageBaseUrl = "http://image.tmdb.org/t/p/w500/";

        private ArrayList<Movie> parseJson(String jsonStr) throws JSONException
        {
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONArray resultsArray = jsonObject.getJSONArray("results");

            imagePaths = new String[resultsArray.length()];
            imageUrls =  new String[resultsArray.length()];

            String title;
            String imageUrl;
            String overview;
            String releaseDate;
            String originalTitle;
            String movieId;

            ArrayList<Movie> movieObjectsArray = new ArrayList<>();

            for(int i=0; i<resultsArray.length(); i++)
            {
                JSONObject movie = resultsArray.getJSONObject(i);

                /*get title of the movie & pass it to our movie object*/
                title = movie.getString("title");

                /*get original title of the movie & pass it to our movie object */
                originalTitle = movie.getString("original_title");

                /*get poster path of the movie & pass it to our movie object*/
                imagePaths[i] = movie.getString("poster_path");

                /*Concatenate image path to the base image url */
                imageUrl = imageBaseUrl+imagePaths[i];

                /*get overview of the movie & pass it to our movie object*/
                overview = movie.getString("overview");

                /*get release date of the movie & pass it to our movie object*/
                releaseDate = movie.getString("release_date");

                /*get movie id*/
                movieId = movie.getString("id");


                Movie movieObject = new Movie(title, imageUrl, overview, releaseDate, originalTitle, movieId);
                movieObjectsArray.add(movieObject);

            }
            for(int i=0; i<resultsArray.length(); i++)
            {
                Log.v(LOG_TAG, i+" "+movieObjectsArray.get(i).getTitle());
            }

            return movieObjectsArray;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<Movie> doInBackground(String... params)
        {
            HttpURLConnection httpURLConnection = null;
            BufferedReader bufferedReader = null;

            String returnedJsonStr=null;

            final String TMBD_BASE_URL  = "https://api.themoviedb.org/3/discover/movie?";
            final String API_KEY        = "api_key";
            final String SORT_BY        = "sort_by";
            final String VOTE_COUNT     = "vote_count.gte";
             /*final String DISCOVER_PARAM = "discover";
            final String MOVIE_PARAM    = "movie?";*/

            String popularity_desc  = "popularity.desc";
            String voteAverage_desc = "vote_average.desc";
            String voteCount        = "400";

            try
            {
                // Building URL
                Uri builtUri=null;
                URL url=null;

                String param = params[0];
                Log.v(LOG_TAG, "Param: "+param);

                if(param.equals("popular"))
                {
                    builtUri = Uri.parse(TMBD_BASE_URL).buildUpon()
                            .appendQueryParameter(API_KEY, api_key)
                            .appendQueryParameter(SORT_BY, popularity_desc)
                            .build();
                    url = new URL(builtUri.toString());

                }
                else
                {
                    builtUri = Uri.parse(TMBD_BASE_URL).buildUpon()
                            .appendQueryParameter(API_KEY, api_key)
                            .appendQueryParameter(VOTE_COUNT, voteCount)
                            .appendQueryParameter(SORT_BY, voteAverage_desc)
                            .build();

                    url = new URL(builtUri.toString());
                }
                Log.v(LOG_TAG, "Built URI " + builtUri.toString());

                // Open http conection
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();

                // Read returned input stream from http connection
                InputStream inputStream = httpURLConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                if(inputStream==null)
                    return null;

                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = bufferedReader.readLine()) != null)
                {

                    buffer.append(line);
                }
                if(buffer.length()==0)
                {
                    return null;
                }
                returnedJsonStr = buffer.toString();

                Log.v(LOG_TAG, "Returned JSON: "+returnedJsonStr);


            }catch (IOException e)
            {
                e.printStackTrace();
            }
            finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            try {
                return parseJson(returnedJsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            if(!movies.isEmpty())
            {
                recyclerViewMovies.setAdapter(new MoviesAdapter(movies));
            }
        }
    }
}
