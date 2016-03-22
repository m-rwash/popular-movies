package com.rwash.popularmovieapp;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import junit.framework.Test;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by bonzo on 3/20/16.
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    private Integer[] images;
    private String[] labels;


    public MoviesAdapter(Integer[] images, String[] labels)
    {
        this.images = images;
        this.labels = labels;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView textView;
        public ImageView imageView;

        public ViewHolder(View itemView)
        {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.grid_item_label);
            imageView = (ImageView) itemView.findViewById(R.id.grid_item_image);
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
        ViewHolder viewHolder = new ViewHolder(movieView);
        return viewHolder;
    }

    // setting the data into grid_item though holder
    @Override
    public void onBindViewHolder(MoviesAdapter.ViewHolder holder, int position)
    {
        TextView textView = holder.textView;
        textView.setText(labels[position]);

        ImageView imageView = holder.imageView;
        imageView.setImageResource(images[position]);
    }

    // returning count of data
    @Override
    public int getItemCount()
    {
        return images.length;
    }

    public static class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }
}
