package com.example.q.pocketmusic.module.home.profile.post;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.data.bean.ask.AskSongPost;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by 鹏君 on 2017/5/4.
 */

public class UserPostAdapter extends RecyclerArrayAdapter<AskSongPost> {
    public UserPostAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(parent);
    }


    class MyViewHolder extends BaseViewHolder<AskSongPost> {
        TextView postTitleTv;
        TextView postContentTv;
        TextView postCommentNumTv;
        TextView postDateTv;

        public MyViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_user_post);
            postTitleTv = $(R.id.post_title_tv);
            postContentTv = $(R.id.post_content_tv);
            postDateTv = $(R.id.post_date_tv);
            postCommentNumTv = $(R.id.post_comment_num_tv);
        }

        @Override
        public void setData(AskSongPost data) {
            super.setData(data);
            postTitleTv.setText(data.getTitle());
            postContentTv.setText(data.getContent());
            postDateTv.setText(data.getCreatedAt());
            postCommentNumTv.setText(String.valueOf(data.getCommentNum()));
        }
    }
}
