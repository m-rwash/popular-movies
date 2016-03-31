package com.rwash.popularmovieapp;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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

/**
 * Created by bonzo on 3/25/16.
 */
public class MovieDetailFragment extends Fragment{

    private String movieTitle         = null;
    private String moviePoster        = null;
    private String movieOverview      = null;
    private String movieReleaseDate   = null;
    private String movieOriginalTitle = null;
    private String movieId            = null;

    private ArrayList<TrailerObject> trailers = new ArrayList<>();
    private ArrayList<ReviewObject> reviews = new ArrayList<>();

    private final String LOG_TAG = MovieDetailFragment.class.getSimpleName();

    private ListView trailersListView;
    private ListView reviewsListView;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        MovieDetailActivity activity = (MovieDetailActivity) getActivity();
        movieTitle         = activity.getMovieTitle();
        moviePoster        = activity.getMoviePoster();
        movieOverview      = activity.getMovieOverview();
        movieReleaseDate   = activity.getMovieReleaseDate();
        movieOriginalTitle = activity.getMovieOriginalTitle();
        movieId            = activity.getMovieId();

        Log.v(LOG_TAG, "movie title: " + movieTitle);
        Log.v(LOG_TAG, "movie release date: " + movieReleaseDate);

    }

    @Override
    public void onStart() {
        super.onStart();
        new FetchTrailers().execute(movieId);
        new FetchReviews().execute(movieId);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        TextView  movieTitleTextView = (TextView) rootView.findViewById(R.id.movieTitleTv);
        movieTitleTextView.setText(movieTitle);

        TextView movieOriginalTitleTextViw = (TextView) rootView.findViewById(R.id.movieOriginalTitleTv);
        movieOriginalTitleTextViw.setText("("+movieOriginalTitle+")");

        TextView movieReleaseDateTextView = (TextView) rootView.findViewById(R.id.movieReleaseDateTv);
        movieReleaseDateTextView.setText(movieReleaseDate);

        TextView movieOverviewTextView = (TextView) rootView.findViewById(R.id.movieOverviewTv);
        movieOverviewTextView.setText(movieOverview);

        ImageView moviePosterImageView = (ImageView) rootView.findViewById(R.id.moviePosterIv);

        Picasso.with(getActivity())
                .load(moviePoster)
                .into(moviePosterImageView);

        trailersListView = (ListView)rootView.findViewById(R.id.trailersLv);

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
        });

        reviewsListView = (ListView) rootView.findViewById(R.id.reviewsLv);

        reviewsListView.setOnTouchListener(new ListView.OnTouchListener() {
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
        });

        return rootView;
    }

    public class FetchReviews extends  AsyncTask<String, Void, ArrayList<ReviewObject>>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        private ArrayList<ReviewObject> parseJson(String jsonStr) throws JSONException
        {
            JSONObject jsonObject  = new JSONObject(jsonStr);
            JSONArray resultsArray = jsonObject.getJSONArray("results");

            ArrayList<ReviewObject> reviewObjects = new ArrayList<ReviewObject>();

            for(int i=0; i<resultsArray.length(); i++)
            {
                JSONObject review = resultsArray.getJSONObject(i);
                String author  = review.getString("author");
                String content = review.getString("content");
                String url     = review.getString("url");

                reviewObjects.add(new ReviewObject(author, content, url));
            }

            return reviewObjects;
        }


        @Override
        protected ArrayList<ReviewObject> doInBackground(String... params)
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
                        .appendQueryParameter(API_KEY, GridFragment.api_key)
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
        protected void onPostExecute(ArrayList<ReviewObject> reviewObjects) {
           if(!reviewObjects.isEmpty())
           {
               reviews = reviewObjects;

               for(ReviewObject review : reviews)
                   Log.v(LOG_TAG, review.toString());

               reviewsListView.setAdapter(new ReviewAdapter(getActivity(), reviews));
           }
        }
    }


    public class FetchTrailers extends AsyncTask<String, Void, ArrayList<TrailerObject>>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        private ArrayList<TrailerObject> parseJson(String jsonStr) throws JSONException
        {
            JSONObject jsonObject  = new JSONObject(jsonStr);
            JSONArray resultsArray = jsonObject.getJSONArray("results");

            ArrayList<TrailerObject> trailerObjects = new ArrayList<TrailerObject>();

            for(int i=0; i<resultsArray.length(); i++)
            {
                JSONObject trailer = resultsArray.getJSONObject(i);
                String key  = trailer.getString("key");
                String name = trailer.getString("name");

                trailerObjects.add(new TrailerObject(key, name));
            }

            return trailerObjects;
        }


        @Override
        protected ArrayList<TrailerObject> doInBackground(String... params) {

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
                        .appendQueryParameter(API_KEY, GridFragment.api_key)
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
        protected void onPostExecute(ArrayList<TrailerObject> trailerObjects) {
            if(!trailerObjects.isEmpty())
            {
                trailers = trailerObjects;

                for(TrailerObject trailer : trailers)
                    Log.v(LOG_TAG, trailer.toString());

                trailersListView.setAdapter(new TrailerAdapter(getActivity(), trailers));

            }

        }
    }


}
