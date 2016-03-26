package com.rwash.popularmovieapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by bonzo on 3/20/16.
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    private static ArrayList<MovieObject> moviesArray = new ArrayList<>();

    public final static String EXTRA_MOVIE_TITLE        = "MOVIE_TITLE";
    public final static String EXTRA_MOVIE_POSTER       = "MOVIE_POSTER";
    public final static String EXTRA_MOVIE_OVERVIEW     = "MOVIE_OVERVIEW";
    public final static String EXTRA_MOVIE_RELEASE_DATE = "MOVIE_RELEASE_DATE";


    public MoviesAdapter(ArrayList<MovieObject> moviesArray)
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

        // handle click from onClickListener
        @Override
        public void onClick(View v)
        {
            /* Initialize intent to pass movie attributes to MovieDetailActivity class*/
            Intent intent = new Intent(context, MovieDetailActivity.class);
            intent.putExtra(EXTRA_MOVIE_TITLE,         moviesArray.get(getAdapterPosition()).getTitle());
            intent.putExtra(EXTRA_MOVIE_POSTER,       moviesArray.get(getAdapterPosition()).getImageUrl());
            intent.putExtra(EXTRA_MOVIE_OVERVIEW,     moviesArray.get(getAdapterPosition()).getOverview());
            intent.putExtra(EXTRA_MOVIE_RELEASE_DATE, moviesArray.get(getAdapterPosition()).getReleaseDate());

            context.startActivity(intent);
            Toast.makeText(context, moviesArray.get(getAdapterPosition()).getTitle(), Toast.LENGTH_SHORT).show();
        }
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
    }

    // returning count of data model we have fetched
    @Override
    public int getItemCount()
    {
        return moviesArray.size();
    }

}
