package com.example.q.pocketmusic.module.home.convert;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.callback.AbsOnClickItemHeadListener;
import com.example.q.pocketmusic.config.pic.GlideStrategy;
import com.example.q.pocketmusic.config.pic.IDisplayStrategy;
import com.example.q.pocketmusic.model.bean.convert.ConvertPost;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;


public class HomeConvertListAdapter extends RecyclerArrayAdapter<ConvertPost> {
    private IDisplayStrategy displayStrategy;
    private AbsOnClickItemHeadListener onClickItemHeadListener;

    public void setOnClickItemHeadListener(AbsOnClickItemHeadListener onClickItemHeadListener) {
        this.onClickItemHeadListener = onClickItemHeadListener;
    }

    public HomeConvertListAdapter(Context context) {
        super(context);
        displayStrategy = new GlideStrategy();
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(parent);

    }

    class MyViewHolder extends BaseViewHolder<ConvertPost> {
        ImageView convertPostUserHeadIv;
        TextView convertPostUserDateTv;
        TextView convertPostUserNameTv;
        TextView convertPostUserTitleTv;
        TextView convertPostUserContentTv;
        TextView convertPostUserCoinTv;
        TextView convertPostUserCommentNumTv;
        LinearLayout contentLl;


        public MyViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_home_convert_list);
            contentLl = $(R.id.content_ll);
            convertPostUserCoinTv = $(R.id.convert_post_user_coin_tv);
            convertPostUserHeadIv = $(R.id.convert_post_user_head_iv);
            convertPostUserDateTv = $(R.id.convert_post_user_date_tv);
            convertPostUserNameTv = $(R.id.convert_post_user_name_tv);
            convertPostUserContentTv = $(R.id.convert_post_user_content_tv);
            convertPostUserTitleTv = $(R.id.convert_post_user_title_tv);
            convertPostUserCommentNumTv = $(R.id.convert_post_user_comment_num_tv);
        }

        @Override
        public void setData(final ConvertPost data) {
            super.setData(data);
            if (data.getUser() == null) {
                return;
            }
            displayStrategy.displayCircle(getContext(), data.getUser().getHeadImg(), convertPostUserHeadIv);
            convertPostUserDateTv.setText(data.getCreatedAt());
            convertPostUserNameTv.setText(data.getUser().getNickName());
            convertPostUserTitleTv.setText("标题：" + data.getTitle());
            convertPostUserCoinTv.setText(String.valueOf(data.getCoin()));
            convertPostUserCommentNumTv.setText(String.valueOf(data.getCommentNum()));
            convertPostUserContentTv.setText("描述：" + data.getContent());
            contentLl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickItemHeadListener != null) {
                        onClickItemHeadListener.onClickItem(getAdapterPosition());
                    }
                }
            });
            convertPostUserHeadIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickItemHeadListener != null) {
                        onClickItemHeadListener.onClickHead(getContext(), data.getUser());
                    }
                }
            });
        }
    }
}