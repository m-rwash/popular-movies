package com.rwash.popularmovieapp.views.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rwash.popularmovieapp.R;
import com.rwash.popularmovieapp.model.Trailer;

import java.util.ArrayList;

/**
 * Created by bonzo on 3/29/16.
 */
public class TrailerAdapter extends BaseAdapter {

    private String key = null;

    private ArrayList<Trailer> trailers;
    private Context context;

    public TrailerAdapter(Context context, ArrayList<Trailer> trailers) {
        this.trailers = trailers;
        this.context  = context;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView == null)
        {
            convertView = inflater.inflate(R.layout.trailer_item, null);

            TextView trailerNameTv = (TextView) convertView.findViewById(R.id.trailerNameTv);
            trailerNameTv.setText(trailers.get(position).getName());

            key = trailers.get(position).getKey();
            final String youtubeBaseUrl = "http://www.youtube.com/watch?";

            ImageView playButtonIv = (ImageView) convertView.findViewById(R.id.playButton);
            playButtonIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse(youtubeBaseUrl).buildUpon()
                                    .appendQueryParameter("v", key)
                                    .build()
                    ));
                    Log.i("Video", "Video Playing....");
                }
            });

        }
        return convertView;
    }

    @Override
    public int getCount() {
        return trailers.size();
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
