package com.rwash.popularmovieapp.views.adapters;

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

import com.rwash.popularmovieapp.R;
import com.rwash.popularmovieapp.model.Trailer;

import java.util.ArrayList;

/**
 * Created by bonzo on 3/29/16.
 */
public class TrailerAdapter extends ArrayAdapter<Trailer> {

    private String key = null;

    public TrailerAdapter(Context context, ArrayList<Trailer> trailers) {
        super(context, 0, trailers);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Trailer trailer = getItem(position);

        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.trailer_item, parent, false);

        TextView trailerNameTv = (TextView) convertView.findViewById(R.id.trailerNameTv);
        trailerNameTv.setText(trailer.getName());

        key = trailer.getKey();
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
