package com.example.q.pocketmusic.util;

import android.content.Context;
import android.widget.ImageView;

import com.example.q.pocketmusic.callback.IDisplayStrategy;

/**
 * Created by Cloud on 2016/12/12.
 */

public class DisplayStrategy {
    private IDisplayStrategy iDisplayStrategy;

    public DisplayStrategy() {
        iDisplayStrategy=new GlideStrategy();
    }

    public void display(Context context, String url, ImageView imageView) {
        this.iDisplayStrategy.display(context, url, imageView);
    }

    public void displayCircle(Context context,String url,ImageView imageView){
        this.iDisplayStrategy.displayCircle(context,url,imageView);
    }
}
