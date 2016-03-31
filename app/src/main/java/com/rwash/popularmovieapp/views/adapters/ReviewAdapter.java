package com.rwash.popularmovieapp.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.rwash.popularmovieapp.R;
import com.rwash.popularmovieapp.model.Review;

import java.util.ArrayList;

/**
 * Created by bonzo on 3/31/16.
 */
public class ReviewAdapter extends ArrayAdapter<Review>{
    public ReviewAdapter(Context context, ArrayList<Review> reviews) {
        super(context, 0, reviews);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Review review = getItem(position);
        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.review_item, parent, false);

        TextView reviewAuthorTextView = (TextView) convertView.findViewById(R.id.reviewAuthorTv);
        reviewAuthorTextView.setText(review.getAuthor()+": ");

        TextView reviewContentTextView = (TextView) convertView.findViewById(R.id.reviewContentTv);
        reviewContentTextView.setText(review.getContent());

        TextView reviewUrlTextView = (TextView) convertView.findViewById(R.id.reviewUrlTv);
        reviewUrlTextView.setText(review.getUrl());


        return convertView;
    }
}
