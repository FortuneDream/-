package com.example.q.pocketmusic.module.home.net.special.list;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.model.bean.special.SpecialColumnSong;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by 鹏君 on 2017/8/24.
 * （￣m￣）
 */

public class SpecialListAdapter extends RecyclerArrayAdapter<SpecialColumnSong> {
    public SpecialListAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(parent);
    }

    class MyViewHolder extends BaseViewHolder<SpecialColumnSong> {
        TextView nameTv;
        TextView contentTv;

        public MyViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_special_song);
            nameTv = $(R.id.name_tv);
            contentTv=$(R.id.subtitle_tv);

        }

        @Override
        public void setData(SpecialColumnSong data) {
            super.setData(data);
            nameTv.setText(data.getName());
            contentTv.setText("暂无");
        }
    }
}
