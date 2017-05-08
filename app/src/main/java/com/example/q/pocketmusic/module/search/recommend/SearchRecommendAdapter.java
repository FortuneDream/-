package com.example.q.pocketmusic.module.search.recommend;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.model.bean.Song;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.List;

/**
 * Created by 鹏君 on 2017/4/25.
 */

public class SearchRecommendAdapter extends TagAdapter<Song> {
    private LayoutInflater inflater;

    public SearchRecommendAdapter(List<Song> datas, Context context) {
        super(datas);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(FlowLayout parent, int position, Song song) {
        TextView tagTv = (TextView) inflater.inflate(R.layout.item_recommend_tag, parent, false);
        tagTv.setText(song.getName());
        return tagTv;
    }
}
