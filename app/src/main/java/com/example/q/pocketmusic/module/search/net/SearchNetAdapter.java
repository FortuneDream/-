package com.example.q.pocketmusic.module.search.net;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.model.bean.Song;
import com.example.q.pocketmusic.model.flag.Text;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by 鹏君 on 2017/4/14.
 */

public class SearchNetAdapter extends RecyclerArrayAdapter<Song> {
    private Context context;

    public SearchNetAdapter(Context context) {
        super(context);
        this.context = context;

    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(parent);
    }

    private static class MyViewHolder extends BaseViewHolder<Song> {
        TextView nameTv;
        TextView contentTv;

        MyViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_search);
            nameTv = $(R.id.name_tv);
            contentTv = $(R.id.content_tv);
        }

        @Override
        public void setData(Song data) {
            super.setData(data);
            nameTv.setText(data.getName());
            if (data.getContent() == null) {
                contentTv.setText("暂无");
            }
            contentTv.setText(data.getContent());
        }
    }
}
