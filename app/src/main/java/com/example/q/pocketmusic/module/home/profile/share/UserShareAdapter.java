package com.example.q.pocketmusic.module.home.profile.share;

import android.content.Context;

import android.view.ViewGroup;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.model.bean.share.ShareSong;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by 鹏君 on 2017/5/26.
 */

public class UserShareAdapter extends RecyclerArrayAdapter<ShareSong> {
    public UserShareAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(parent);
    }

    class MyViewHolder extends BaseViewHolder<ShareSong> {
        TextView nameTv;
        TextView contentTv;

        public MyViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_user_share);
            nameTv = $(R.id.name_tv);
            contentTv = $(R.id.content_tv);
        }

        @Override
        public void setData(ShareSong data) {
            super.setData(data);
            nameTv.setText(data.getName());
            contentTv.setText(data.getContent());
        }
    }
}
