package com.example.q.pocketmusic.module.home.profile.gift;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.data.bean.bmob.Gift;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by 81256 on 2017/9/6.
 */

public class GiftAdapter extends RecyclerArrayAdapter<Gift> {
    public GiftAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(parent);
    }

    class MyViewHolder extends BaseViewHolder<Gift> {
        TextView contentTv;
        TextView coinTv;
        TextView dateTv;

        public MyViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_gift);
            contentTv = $(R.id.content_tv);
            coinTv = $(R.id.coin_tv);

            dateTv = $(R.id.date_tv);
        }

        @Override
        public void setData(Gift data) {
            super.setData(data);
            contentTv.setText(data.getContent());
            coinTv.setText(data.getCoin() + "枚");

            dateTv.setText(data.getCreatedAt());
        }
    }
}
