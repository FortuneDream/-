package com.example.q.pocketmusic.config.pic;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by 鹏君 on 2016/12/12.
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
