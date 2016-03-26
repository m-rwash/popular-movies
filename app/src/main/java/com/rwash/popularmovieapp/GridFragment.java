package com.rwash.popularmovieapp;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class GridFragment extends Fragment {
    /*
    * https://api.themoviedb.org/3/discover/movie?api_key=aeab5ec62e5555d42ace5362024cbbaf&/discover/movie?sort_by=vote_average.desc
    * */

    private RecyclerView recyclerViewMovies;

    public GridFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        new FetchMovies().execute();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_grid, container, false);

        recyclerViewMovies = (RecyclerView) rootView.findViewById(R.id.rVmovies);
        recyclerViewMovies.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerViewMovies.setHasFixedSize(true);

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        // inflate menu xml
        inflater.inflate(R.menu.grid_fragment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // if refresh button pressed
        int id = item.getItemId();
        if(id == R.id.action_refresh)
        {
            new FetchMovies().execute();
        }
        return super.onOptionsItemSelected(item);
    }

    /* Fetching Movies data from TMBD api in background thread by extending AsyncTask class */
    public class FetchMovies extends AsyncTask<Void, Void, ArrayList<MovieObject>>
    {
        private final String LOG_TAG = "FetchMovies Class";

        private String[] imagePaths;
        private String[] imageUrls;

        /*Base image url*/
        private final String imageBaseUrl = "http://image.tmdb.org/t/p/w500/";

        private ArrayList<MovieObject> parseJson(String jsonStr) throws JSONException
        {
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONArray resultsArray = jsonObject.getJSONArray("results");

            imagePaths = new String[resultsArray.length()];
            imageUrls =  new String[resultsArray.length()];

            String title;
            String imageUrl;
            String overview;
            String releaseDate;

            ArrayList<MovieObject> movieObjectsArray = new ArrayList<>();

            for(int i=0; i<resultsArray.length(); i++)
            {
                JSONObject movie = resultsArray.getJSONObject(i);

                /*get title of the movie & set it in titles array*/
                title = movie.getString("original_title");

                /*get poster path of the movie & set it in imagePaths array*/
                imagePaths[i] = movie.getString("poster_path");

                /*Concatenate image path to the base image url */
                imageUrl = imageBaseUrl+imagePaths[i];

                /*get overview of the movie*/
                overview = movie.getString("overview");

                /*get release date of the movie*/
                releaseDate = movie.getString("release_date");

                MovieObject movieObject = new MovieObject(title, imageUrl, overview, releaseDate);
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
        protected ArrayList<MovieObject> doInBackground(Void... params)
        {
            HttpURLConnection httpURLConnection = null;
            BufferedReader bufferedReader = null;

            String returnedJsonStr=null;

            String api_key = "aeab5ec62e5555d42ace5362024cbbaf";

            final String TMBD_BASE_URL = "https://api.themoviedb.org/3/discover/movie?";
            final String DISCOVER_PARAM = "discover";
            final String MOVIE_PARAM = "movie?";
            final String API_KEY = "api_key";

            try
            {
                // Building URL
                Uri builtUri = Uri.parse(TMBD_BASE_URL).buildUpon()
                        .appendQueryParameter(API_KEY, api_key)
                        .build();

                URL url = new URL(builtUri.toString());
                Log.v(LOG_TAG, "Built URI " + builtUri.toString());

                // Open http conection
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();

                // Read returned input stream from http connection
                InputStream inputStream = httpURLConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                if(inputStream==null)
                {
                    return null;
                }
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = bufferedReader.readLine()) != null) {

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
        protected void onPostExecute(ArrayList<MovieObject> movieObjects) {
            if(!movieObjects.isEmpty())
            {
                recyclerViewMovies.setAdapter(new MoviesAdapter(movieObjects));
            }
        }
    }

}
