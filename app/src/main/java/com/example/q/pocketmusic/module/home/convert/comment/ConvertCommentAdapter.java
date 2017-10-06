package com.example.q.pocketmusic.module.home.convert.comment;

import android.content.Context;
import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.callback.AbsOnClickItemHeadListener;
import com.example.q.pocketmusic.config.pic.GlideStrategy;
import com.example.q.pocketmusic.config.pic.IDisplayStrategy;
import com.example.q.pocketmusic.model.bean.convert.ConvertComment;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;


public class ConvertCommentAdapter extends RecyclerArrayAdapter<ConvertComment> {
    private IDisplayStrategy displayStrategy;
    private AbsOnClickItemHeadListener absOnClickItemHeadListener;

    public void setAbsOnClickItemHeadListener(AbsOnClickItemHeadListener absOnClickItemHeadListener) {
        this.absOnClickItemHeadListener = absOnClickItemHeadListener;
    }

    public ConvertCommentAdapter(Context context) {
        super(context);
        displayStrategy = new GlideStrategy();
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(parent);

    }

    class MyViewHolder extends BaseViewHolder<ConvertComment> {
        ImageView userHeadIv;
        TextView userNameTv;
        TextView dateTv;
        TextView checkPartConvertSongTv;
        LinearLayout contentLl;

        public MyViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_convert_comment_adapter);
            userHeadIv = $(R.id.user_head_iv);
            userNameTv = $(R.id.user_name_tv);
            dateTv = $(R.id.date_tv);
            contentLl = $(R.id.content_ll);
            checkPartConvertSongTv = $(R.id.check_part_convert_song_tv);
            contentLl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (absOnClickItemHeadListener != null) {
                        absOnClickItemHeadListener.onClickItem(getAdapterPosition() - 1);
                    }
                }
            });
        }

        @Override
        public void setData(final ConvertComment data) {
            super.setData(data);
            displayStrategy.displayCircle(getContext(), data.getUser().getHeadImg(), userHeadIv);
            userNameTv.setText(data.getUser().getNickName());
            dateTv.setText(data.getCreatedAt());
            userHeadIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (absOnClickItemHeadListener != null) {
                        absOnClickItemHeadListener.onClickHead(getContext(), data.getUser());
                    }
                }
            });
        }
    }
}