package com.example.q.pocketmusic.module.home.net.special;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.config.pic.GlideStrategy;
import com.example.q.pocketmusic.config.pic.IDisplayStrategy;
import com.example.q.pocketmusic.model.bean.special.SpecialColumn;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by 鹏君 on 2017/7/26.
 * （￣m￣）
 */

public class SpecialColumnAdapter extends RecyclerArrayAdapter<SpecialColumn> {
    private IDisplayStrategy displayStrategy;

    public SpecialColumnAdapter(Context context) {
        super(context);
        displayStrategy = new GlideStrategy();
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(parent);
    }

    class MyViewHolder extends BaseViewHolder<SpecialColumn> {
        ImageView backgroundIv;
        TextView titleTv;
        TextView numberTv;

        public MyViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_acg_album);
            backgroundIv = $(R.id.background_iv);
            titleTv = $(R.id.title_tv);
            numberTv = $(R.id.number_tv);
        }

        @Override
        public void setData(SpecialColumn data) {
            super.setData(data);
            displayStrategy.display(getContext(), data.getBackgroundUrl(), backgroundIv);
            titleTv.setText(data.getAlbumName());
            numberTv.setText("曲谱数：" + String.valueOf(data.getSongNum()));
        }
    }
}
