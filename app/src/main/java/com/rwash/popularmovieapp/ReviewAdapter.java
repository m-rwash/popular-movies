package com.rwash.popularmovieapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by bonzo on 3/31/16.
 */
public class ReviewAdapter extends ArrayAdapter<ReviewObject>{
    public ReviewAdapter(Context context, ArrayList<ReviewObject> reviewObjects) {
        super(context, 0, reviewObjects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ReviewObject reviewObject = getItem(position);
        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.review_item, parent, false);

        TextView reviewAuthorTextView = (TextView) convertView.findViewById(R.id.reviewAuthorTv);
        reviewAuthorTextView.setText(reviewObject.getAuthor()+": ");

        TextView reviewContentTextView = (TextView) convertView.findViewById(R.id.reviewContentTv);
        reviewContentTextView.setText(reviewObject.getContent());

        TextView reviewUrlTextView = (TextView) convertView.findViewById(R.id.reviewUrlTv);
        reviewUrlTextView.setText(reviewObject.getUrl());


        return convertView;
    }
}
