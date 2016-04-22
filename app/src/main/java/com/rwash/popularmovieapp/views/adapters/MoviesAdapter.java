package com.rwash.popularmovieapp.views.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rwash.popularmovieapp.MainActivity;
import com.rwash.popularmovieapp.MovieDetailActivity;
import com.rwash.popularmovieapp.R;
import com.rwash.popularmovieapp.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by bonzo on 3/20/16.
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder>
{

    private static ArrayList<Movie> moviesArray = new ArrayList<>();

    public final static String EXTRA_MOVIE_TITLE          = "MOVIE_TITLE";
    public final static String EXTRA_MOVIE_POSTER         = "MOVIE_POSTER";
    public final static String EXTRA_MOVIE_OVERVIEW       = "MOVIE_OVERVIEW";
    public final static String EXTRA_MOVIE_RELEASE_DATE   = "MOVIE_RELEASE_DATE";
    public final static String EXTRA_MOVIE_ORIGINAL_TITLE = "MOVIE_ORIGINAL_TITLE";
    public final static String EXTRA_MOVIE_ID             = "MOVIE_ID";

    private static String movieTitle         = null;
    private static String moviePoster        = null;
    private static String movieOverview      = null;
    private static String movieReleaseDate   = null;
    private static String movieOriginalTitle = null;
    private static String movieId            = null;

    private static ClickListener clickListener;

    public interface ClickListener
    {
        void onItemClick(int position, View view);
    }

    public void setClickListener(ClickListener clickListener)
    {
        this.clickListener = clickListener;
    }
    static int i;

    public MoviesAdapter(ArrayList<Movie> moviesArray)
    {
        this.moviesArray = moviesArray;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView textView;
        public ImageView imageView;
        private Context context;

        public ViewHolder(Context context, View itemView)
        {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.grid_item_label);
            imageView = (ImageView) itemView.findViewById(R.id.grid_item_image);

            this.context = context;
            // set onClickListener
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view)
        {
            if(clickListener != null)
                clickListener.onItemClick(getLayoutPosition(), view);
        }

        /*

        *//*
        // handle click from onClickListener
        @Override
        public void onClick(View v)
        {
            *//* Initialize intent to pass movie attributes to MovieDetailActivity class*//*

            *//*Intent intent = new Intent(context, MovieDetailActivity.class);
            intent.putExtra(EXTRA_MOVIE_TITLE,          moviesArray.get(getAdapterPosition()).getTitle());
            intent.putExtra(EXTRA_MOVIE_POSTER,         moviesArray.get(getAdapterPosition()).getImageUrl());
            intent.putExtra(EXTRA_MOVIE_OVERVIEW,       moviesArray.get(getAdapterPosition()).getOverview());
            intent.putExtra(EXTRA_MOVIE_RELEASE_DATE,   moviesArray.get(getAdapterPosition()).getReleaseDate());
            intent.putExtra(EXTRA_MOVIE_ORIGINAL_TITLE, moviesArray.get(getAdapterPosition()).getOriginalTitle());
            intent.putExtra(EXTRA_MOVIE_ID,             moviesArray.get(getAdapterPosition()).getMovieId());

            context.startActivity(intent);*//*

            Movie movie = new Movie(
                    moviesArray.get(getAdapterPosition()).getTitle(),
                    moviesArray.get(getAdapterPosition()).getImageUrl(),
                    moviesArray.get(getAdapterPosition()).getOverview(),
                    moviesArray.get(getAdapterPosition()).getReleaseDate(),
                    moviesArray.get(getAdapterPosition()).getOriginalTitle(),
                    moviesArray.get(getAdapterPosition()).getMovieId()
            );



            MainActivity mainActivity = new MainActivity();
            mainActivity.setMovie(movie);



*//*
            movieTitle          = moviesArray.get(getAdapterPosition()).getTitle();
            moviePoster         = moviesArray.get(getAdapterPosition()).getImageUrl();
            movieOverview       = moviesArray.get(getAdapterPosition()).getOverview();
            movieReleaseDate    = moviesArray.get(getAdapterPosition()).getReleaseDate();
            movieOriginalTitle  = moviesArray.get(getAdapterPosition()).getOriginalTitle();
            movieId             = moviesArray.get(getAdapterPosition()).getMovieId();

            if(MainActivity.twoPane==false)
            {
                Intent intent = new Intent(context, MovieDetailActivity.class);
                context.startActivity(intent);
            }
*//*
            //Toast.makeText(context, moviesArray.get(getAdapterPosition()).getTitle(), Toast.LENGTH_SHORT).show();
        }*/

    }


    // Inflating from layout xml and returning holder
    @Override
    public MoviesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        // 1. Inflate grid_item layout

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View movieView = inflater.inflate(R.layout.grid_item, parent, false);

        // 2. Create ViewHolder
        ViewHolder viewHolder = new ViewHolder(context, movieView);
        return viewHolder;
    }

    // setting the data into grid_item though holder
    @Override
    public void onBindViewHolder(MoviesAdapter.ViewHolder holder, int position)
    {
        // Setting titles (movies titles)
        TextView textView = holder.textView;
            //textView.setText(titles[position]);
        textView.setText(moviesArray.get(position).getTitle());

        // Setting images (movies posters) using Picasso library
        Picasso.with(holder.context)
               .load(moviesArray.get(position).getImageUrl())
               .into(holder.imageView);

    /*    if(position == 0)
        {
            movieTitle          = moviesArray.get(holder.getAdapterPosition()).getTitle();
            moviePoster         = moviesArray.get(holder.getAdapterPosition()).getImageUrl();
            movieOverview       = moviesArray.get(holder.getAdapterPosition()).getOverview();
            movieReleaseDate    = moviesArray.get(holder.getAdapterPosition()).getReleaseDate();
            movieOriginalTitle  = moviesArray.get(holder.getAdapterPosition()).getOriginalTitle();
            movieId             = moviesArray.get(holder.getAdapterPosition()).getMovieId();
        }*/


    }

    // returning count of data model we have fetched
    @Override
    public int getItemCount()
    {
        return moviesArray.size();
    }

    public static String getMovieTitle() {
        return movieTitle;
    }

    public static String getMovieOverview() {
        return movieOverview;
    }

    public static String getMoviePoster() {
        return moviePoster;
    }

    public static String getMovieReleaseDate() {
        return movieReleaseDate;
    }

    public static String getMovieOriginalTitle() {
        return movieOriginalTitle;
    }

    public static String getMovieId() {
        return movieId;
    }

}
