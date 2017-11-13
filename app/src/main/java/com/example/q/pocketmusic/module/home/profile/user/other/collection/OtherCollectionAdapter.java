package com.example.q.pocketmusic.module.home.profile.user.other.collection;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.data.bean.collection.CollectionSong;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by 鹏君 on 2017/7/24.
 * （￣m￣）
 */

public class OtherCollectionAdapter extends RecyclerArrayAdapter<CollectionSong> {
    public OtherCollectionAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent);
    }

    class ViewHolder extends BaseViewHolder<CollectionSong> {
        TextView nameTv;
        TextView contentTv;

        public ViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_other_collection);
            nameTv = $(R.id.collection_name_tv);
            contentTv = $(R.id.collection_content_tv);
        }

        @Override
        public void setData(CollectionSong data) {
            super.setData(data);
            nameTv.setText(data.getName());
            contentTv.setText(data.getContent());
        }
    }
}
