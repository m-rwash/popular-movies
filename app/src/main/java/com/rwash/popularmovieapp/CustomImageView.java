package com.rwash.popularmovieapp;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class CustomImageView extends ImageView {
    public CustomImageView(Context context) {
        super(context);
    }

    public CustomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        double customeHeight = getMeasuredWidth()*0.5;
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth()+(int)customeHeight); //Snap to width
    }
}