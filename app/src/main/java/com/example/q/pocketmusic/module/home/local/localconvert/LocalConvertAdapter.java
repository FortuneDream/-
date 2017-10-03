package com.example.q.pocketmusic.module.home.local.localconvert;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.model.bean.local.LocalConvertSong;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;


public class LocalConvertAdapter extends RecyclerArrayAdapter<LocalConvertSong> {


    public LocalConvertAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(parent);

    }

    class MyViewHolder extends BaseViewHolder<LocalConvertSong> {
        private TextView nameTv;
        private TextView moreIv;

        public MyViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_local_convert_adapter);
            nameTv=$(R.id.name_tv);
            moreIv=$(R.id.more_iv);
        }

        @Override
        public void setData(LocalConvertSong data) {
            super.setData(data);
            nameTv.setText(data.getName());
        }
    }
}