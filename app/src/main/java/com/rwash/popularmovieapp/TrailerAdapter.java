package com.rwash.popularmovieapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by bonzo on 3/29/16.
 */
public class TrailerAdapter extends ArrayAdapter<TrailerObject> {

    private String key = null;

    public TrailerAdapter(Context context, ArrayList<TrailerObject> trailers) {
        super(context, 0, trailers);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TrailerObject trailerObject = getItem(position);

        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.trailer_item, parent, false);

        TextView trailerNameTv = (TextView) convertView.findViewById(R.id.trailerNameTv);
        trailerNameTv.setText(trailerObject.getName());

        key = trailerObject.getKey();
        final String youtubeBaseUrl = "http://www.youtube.com/watch?";

        ImageView playButtonIv = (ImageView) convertView.findViewById(R.id.playButton);
        playButtonIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                getContext().startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse(youtubeBaseUrl).buildUpon()
                        .appendQueryParameter("v",key)
                        .build()
                ));
                Log.i("Video", "Video Playing....");
            }
        });

        return convertView;
    }
}
