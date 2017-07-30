package com.example.q.pocketmusic.module.home.seek.ask.comment;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.callback.AbsOnClickItemHeadListener;
import com.example.q.pocketmusic.model.bean.ask.AskSongComment;
import com.example.q.pocketmusic.config.pic.DisplayStrategy;
import com.example.q.pocketmusic.util.common.LogUtils;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by 鹏君 on 2017/1/12.
 */

public class AskSongCommentAdapter extends RecyclerArrayAdapter<AskSongComment> {
    private DisplayStrategy displayStrategy;
    private Context context;
    private AbsOnClickItemHeadListener absOnClickItemHeadListener;

    public void setAbsOnClickItemHeadListener(AbsOnClickItemHeadListener absOnClickItemHeadListener) {
        this.absOnClickItemHeadListener = absOnClickItemHeadListener;
    }

    public AskSongCommentAdapter(Context context) {
        super(context);
        this.context = context;
        displayStrategy = new DisplayStrategy();
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(parent);
    }

    class MyViewHolder extends BaseViewHolder<AskSongComment> {
        TextView userContentTv;
        TextView userNameTv;
        TextView hasPicTv;
        TextView agreeTv;
        TextView dateTv;
        ImageView userHeadIv;
        LinearLayout contentLl;

        public MyViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_ask_song_comment);
            userContentTv = $(R.id.user_content_tv);
            userNameTv = $(R.id.user_name_tv);
            userHeadIv = $(R.id.user_head_iv);
            hasPicTv = $(R.id.has_pic_tv);
            agreeTv = $(R.id.agree_tv);
            dateTv = $(R.id.date_tv);
            contentLl = $(R.id.content_ll);
            contentLl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (absOnClickItemHeadListener != null) {
                        absOnClickItemHeadListener.onClickItem(getAdapterPosition()-1);
                    }
                }
            });
        }

        @Override
        public void setData(final AskSongComment data) {
            super.setData(data);
            dateTv.setText(data.getCreatedAt());
            userContentTv.setText(data.getContent());
            userNameTv.setText(data.getUser().getNickName());
            displayStrategy.displayCircle(context, data.getUser().getHeadImg(), userHeadIv);
            isShowMorePic(data);
            userHeadIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (absOnClickItemHeadListener != null) {
                        absOnClickItemHeadListener.onClickHead(getContext(), data.getUser());
                    }
                }
            });
        }

        private void isShowMorePic(AskSongComment data) {
            if (!data.getHasPic()) {
                hasPicTv.setVisibility(View.GONE);
                agreeTv.setVisibility(View.GONE);
            } else {
                hasPicTv.setVisibility(View.VISIBLE);
                agreeTv.setVisibility(View.VISIBLE);
                agreeTv.setText("点赞数：" + data.getAgreeNum());//只有有图的时候才会显示点赞数
            }
        }

    }
}
