package com.example.q.pocketmusic.module.home.convert.comment;

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
import com.example.q.pocketmusic.model.bean.convert.ConvertComment;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import butterknife.BindView;


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
        ImageView convertCommentUserHeadIv;
        TextView convertCommentUserNameTv;
        TextView convertCommentUserTitleTv;
        TextView convertCommentUserDateTv;
        TextView checkPartConvertSongTv;
        LinearLayout contentLl;

        public MyViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_convert_comment_adapter);
            convertCommentUserHeadIv = $(R.id.convert_comment_user_head_iv);
            convertCommentUserNameTv = $(R.id.convert_comment_user_name_tv);
            convertCommentUserTitleTv = $(R.id.convert_comment_user_title_tv);
            convertCommentUserDateTv = $(R.id.convert_comment_user_date_tv);
            checkPartConvertSongTv = $(R.id.check_part_convert_song_tv);
            contentLl = $(R.id.content_ll);
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
            displayStrategy.displayCircle(getContext(), data.getUser().getHeadImg(), convertCommentUserHeadIv);
            convertCommentUserNameTv.setText(data.getUser().getNickName());
            convertCommentUserDateTv.setText(data.getCreatedAt());
            convertCommentUserTitleTv.setText(data.getTitle());
            convertCommentUserHeadIv.setOnClickListener(new View.OnClickListener() {
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