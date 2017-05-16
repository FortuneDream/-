package com.example.q.pocketmusic.module.home.seek.ask.comment;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.callback.IDisplayStrategy;
import com.example.q.pocketmusic.util.GlideStrategy;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by Cloud on 2017/2/5.
 */

public class PicAdapter extends RecyclerArrayAdapter<String> {
    private Context context;
    private IDisplayStrategy displayStrategy;

    public PicAdapter(Context context) {
        super(context);
        this.context = context;
        displayStrategy = new GlideStrategy();
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(parent);
    }


    class MyViewHolder extends BaseViewHolder<String> {
        ImageView pic;

        public MyViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_song);
            pic = $(R.id.pic_iv);
        }

        @Override
        public void setData(String data) {
            super.setData(data);
            displayStrategy.display(context, data, pic);
        }
    }
}
