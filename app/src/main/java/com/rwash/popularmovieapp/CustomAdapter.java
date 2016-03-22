package com.rwash.popularmovieapp;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {

    private Context context;
    private Integer[] images;
    private String[] labels;


    public CustomAdapter(Context context, Integer[] images, String[] labels) {
        this.context = context;
        this.images = images;
        this.labels = labels;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View myView;

        if (convertView == null) {
            myView = new View(context);
            myView = inflater.inflate(R.layout.grid_item, null);

            TextView textView = (TextView) myView.findViewById(R.id.grid_item_label);
            textView.setText(labels[position]);

            ImageView imageView = (ImageView) myView.findViewById(R.id.grid_item_image);
            imageView.setImageResource(images[position]);
            imageView.setPadding(8, 8, 8, 8);
           // imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);


            convertView = inflater.inflate(R.layout.grid_item, parent, false);
        }else
        {
            myView = (View) convertView;
        }
        return myView;
    }


    public int getCount() {
        return images.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }
}
