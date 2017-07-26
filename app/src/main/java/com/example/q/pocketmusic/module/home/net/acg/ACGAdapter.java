package com.example.q.pocketmusic.module.home.net.acg;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.config.pic.GlideStrategy;
import com.example.q.pocketmusic.config.pic.IDisplayStrategy;
import com.example.q.pocketmusic.model.bean.acg.ACGAlbum;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by 鹏君 on 2017/7/26.
 * （￣m￣）
 */

public class ACGAdapter extends RecyclerArrayAdapter<ACGAlbum> {
    private IDisplayStrategy displayStrategy;

    public ACGAdapter(Context context) {
        super(context);
        displayStrategy = new GlideStrategy();
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(parent);
    }

    class MyViewHolder extends BaseViewHolder<ACGAlbum> {
        ImageView backgroundIv;
        TextView titleTv;

        public MyViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_acg_album);
            backgroundIv = $(R.id.background_iv);
            titleTv = $(R.id.title_tv);
        }

        @Override
        public void setData(ACGAlbum data) {
            super.setData(data);
            displayStrategy.display(getContext(), data.getBackgroundUrl(), backgroundIv);
            titleTv.setText(data.getAlbumName());
        }
    }
}
