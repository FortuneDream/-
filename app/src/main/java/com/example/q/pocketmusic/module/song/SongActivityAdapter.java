package com.example.q.pocketmusic.module.song;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.config.constant.Constant;
import com.example.q.pocketmusic.config.pic.DisplayStrategy;

import java.io.File;
import java.util.List;

import uk.co.senab.photoview.PhotoView; /**
 * Created by 鹏君 on 2016/8/30.
 */

/**
 * 某一首歌的图片列表Adapter
 */
public class SongActivityAdapter extends PagerAdapter {
    private DisplayStrategy displayStrategy;
    private List<String> list;
    private int isFrom;
    private Context context;

    public SongActivityAdapter(Context context, List<String> list, int isFrom) {
        this.context = context;
        this.list = list;
        this.isFrom = isFrom;
        displayStrategy = new DisplayStrategy();
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        PhotoView photoView = new PhotoView(container.getContext());
        String url = list.get(position);
        if (isFrom == Constant.NET) {
            //Glide
            displayStrategy.display(context, url, photoView);
            photoView.setTag(R.id.image_tag);
        } else if (isFrom == Constant.LOCAL) {
            photoView.setImageURI(Uri.fromFile(new File(url)));
        }
        container.addView(photoView, ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.MATCH_PARENT);
        return photoView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


}
