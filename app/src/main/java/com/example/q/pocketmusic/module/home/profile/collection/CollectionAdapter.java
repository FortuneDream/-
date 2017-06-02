package com.example.q.pocketmusic.module.home.profile.collection;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.model.bean.collection.CollectionSong;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by 鹏君 on 2016/11/15.
 */

public class CollectionAdapter extends RecyclerArrayAdapter<CollectionSong> {
    public CollectionAdapter(Context context) {
        super(context);
    }

    private OnSelectListener listener;

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        this.listener = onSelectListener;
    }

    public interface OnSelectListener {
        void onSelectMore(int position);

        void onSelectItem(int position);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(parent);
    }

    class MyViewHolder extends BaseViewHolder<CollectionSong> {
        TextView nameTv;
        TextView contentTv;
        ImageView moreIv;
        RelativeLayout contentRl;

        public MyViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_collection);
            nameTv = $(R.id.name_tv);
            contentTv = $(R.id.content_tv);
            moreIv = $(R.id.more_iv);
            contentRl = $(R.id.content_rl);
        }

        @Override
        public void setData(CollectionSong data) {
            super.setData(data);
            nameTv.setText("曲谱名：" + data.getName());
            contentTv.setText("描述：" + data.getContent());
            contentRl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onSelectItem(getAdapterPosition());
                    }
                }
            });
            moreIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onSelectMore(getAdapterPosition());
                    }
                }
            });
        }

    }
}
