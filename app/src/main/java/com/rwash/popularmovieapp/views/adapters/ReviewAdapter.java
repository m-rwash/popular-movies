package com.rwash.popularmovieapp.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rwash.popularmovieapp.R;
import com.rwash.popularmovieapp.model.Review;

import java.util.ArrayList;

/**
 * Created by bonzo on 3/31/16.
 */
public class ReviewAdapter extends BaseAdapter{

    private ArrayList<Review> reviews;
    private Context context;

    public ReviewAdapter(Context context, ArrayList<Review> reviews) {
        this.reviews = reviews;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView == null)
        {
            convertView = inflater.inflate(R.layout.review_item, null);

            TextView reviewAuthorTextView = (TextView) convertView.findViewById(R.id.reviewAuthorTv);
            reviewAuthorTextView.setText(reviews.get(position).getAuthor()+": ");

            TextView reviewContentTextView = (TextView) convertView.findViewById(R.id.reviewContentTv);
            reviewContentTextView.setText(reviews.get(position).getContent());

            TextView reviewUrlTextView = (TextView) convertView.findViewById(R.id.reviewUrlTv);
            reviewUrlTextView.setText(reviews.get(position).getUrl());

        }
        return convertView;
    }

    @Override
    public int getCount() {
        return reviews.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
