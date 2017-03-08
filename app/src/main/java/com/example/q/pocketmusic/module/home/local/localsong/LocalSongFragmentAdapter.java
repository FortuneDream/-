package com.example.q.pocketmusic.module.home.local.localsong;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.q.pocketmusic.R;

import com.example.q.pocketmusic.model.bean.local.LocalSong;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by YQ on 2016/8/28.
 */

/**
 * 我的乐谱界面Adapter
 */
public class LocalSongFragmentAdapter extends RecyclerArrayAdapter<LocalSong> {
    private OnItemSelectListener onUploadListener;

    public interface OnItemSelectListener {
        void onSelected(int position);
    }

    public void setOnSelectListener(OnItemSelectListener onUploadListener) {
        this.onUploadListener = onUploadListener;
    }

    public LocalSongFragmentAdapter(Context context) {
        super(context);
    }


    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(parent);
    }

    class MyViewHolder extends BaseViewHolder<LocalSong> {
        TextView nameTv;
        TextView dateTv;
        ImageView moreIv;

        MyViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_local_song);
            nameTv = $(R.id.name_tv);
            dateTv = $(R.id.date_tv);
            moreIv = $(R.id.more_iv);

        }

        @Override
        public void setData(LocalSong data) {
            super.setData(data);
            nameTv.setText(data.getName());
            dateTv.setText(data.getDate());
            moreIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onUploadListener!=null){
                        onUploadListener.onSelected(getAdapterPosition());
                    }
                }
            });
        }
    }
}
