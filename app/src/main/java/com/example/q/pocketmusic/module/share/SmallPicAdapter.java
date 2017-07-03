package com.example.q.pocketmusic.module.share;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.config.pic.DisplayStrategy;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by 鹏君 on 2016/11/10.
 */

public class SmallPicAdapter extends RecyclerArrayAdapter<String> {
    private Context context;
    private DisplayStrategy displayStrategy;

    public SmallPicAdapter(Context context) {
        super(context);
        this.context = context;
        displayStrategy = new DisplayStrategy();
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(parent);
    }


    class MyViewHolder extends BaseViewHolder<String> {
        ImageView picIv;

        public MyViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_small_pic);
            picIv = $(R.id.pic_iv);
        }

        @Override
        public void setData(String data) {
            super.setData(data);
            displayStrategy.display(context, data, picIv);
        }
    }
}
