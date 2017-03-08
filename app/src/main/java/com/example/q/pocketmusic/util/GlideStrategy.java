package com.example.q.pocketmusic.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.callback.IDisplayStrategy;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;


/**
 * Created by Cloud on 2016/12/12.
 */

public class GlideStrategy implements IDisplayStrategy {
    @Override
    public void display(Context context,String url, ImageView imageView) {
        Glide
                .with(context)
                .load(url)
                .placeholder(R.drawable.loading_bg)
                .crossFade()
                .fitCenter()
                .into(imageView);
    }

    @Override
    public void displayCircle(final Context context, String url, final ImageView imageView) {
        Glide.with(context)
                .load(url)
                .placeholder(R.mipmap.ico_launcher)
                .bitmapTransform(new CropCircleTransformation(context))
                .into(imageView);
    }

    @Override
    public void displayCorner(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .placeholder(R.mipmap.ico_launcher)
                .bitmapTransform(new RoundedCornersTransformation(context,5,5))
                .into(imageView);
    }
}
