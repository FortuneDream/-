package com.example.q.pocketmusic.module.home.net.type;

import android.content.Context;

import android.view.ViewGroup;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.model.bean.Song;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;


public class SongTypeActivityAdapter extends RecyclerArrayAdapter<Song> {
    private Context context;

    public SongTypeActivityAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(parent);
    }

    class MyViewHolder extends BaseViewHolder<Song> {
        TextView nameTv;
        TextView artistTv;


        public MyViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_song_list);
            nameTv = $(R.id.name_tv);
            artistTv = $(R.id.artist_tv);
        }

        @Override
        public void setData(Song data) {
            super.setData(data);
            nameTv.setText(data.getName());
            if (data.getArtist() != null) {
                artistTv.setText("描述：" + data.getArtist());
            } else {
                artistTv.setText("描述：暂无");
            }
        }
    }

}
