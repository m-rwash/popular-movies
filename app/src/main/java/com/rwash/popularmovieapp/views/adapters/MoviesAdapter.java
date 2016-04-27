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

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by bonzo on 3/20/16.
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder>
{

    private static ArrayList<Movie> moviesArray = new ArrayList<>();

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
       //public TextView textView;
       //public ImageView imageView;
        private Context context;

        @BindView(R.id.grid_item_label) TextView textView;
        @BindView(R.id.grid_item_image) ImageView imageView;

        public ViewHolder(Context context, View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
            //textView = (TextView) itemView.findViewById(R.id.grid_item_label);
            //imageView = (ImageView) itemView.findViewById(R.id.grid_item_image);

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
