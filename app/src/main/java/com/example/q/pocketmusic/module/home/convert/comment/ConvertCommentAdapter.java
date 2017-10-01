package com.example.q.pocketmusic.module.home.convert.comment;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.model.bean.convert.ConvertComment;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;


public class ConvertCommentAdapter extends RecyclerArrayAdapter<ConvertComment> {


    public ConvertCommentAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(parent);

    }

    class MyViewHolder extends BaseViewHolder<ConvertComment> {
        public MyViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_convert_comment_adapter);

        }

        @Override
        public void setData(ConvertComment data) {
            super.setData(data);

        }
    }
}