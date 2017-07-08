package com.example.q.pocketmusic.module.home.seek.ask;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.ask.AskSongPost;

import com.example.q.pocketmusic.config.pic.DisplayStrategy;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by 鹏君 on 2017/1/26.
 */

public class AskListAdapter extends RecyclerArrayAdapter<AskSongPost> {
    private DisplayStrategy displayStrategy;
    private Context context;
    private OnItemClickListener listener;


    public AskListAdapter(Context context) {
        super(context);
        this.context = context;
        displayStrategy = new DisplayStrategy();
    }

    public void setListener(OnItemClickListener onItemClickListener) {
        this.listener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClickHeadIv(int position);

        void onClickContent(int position);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(parent);
    }

    class MyViewHolder extends BaseViewHolder<AskSongPost> {
        TextView postUserTitleTv;
        TextView postUserContentTv;
        TextView postUserTypeTv;
        TextView postUserNameTv;
        TextView postUserDateTv;
        TextView postUserCommentNumTv;
        ImageView postUserHeadIv;
        LinearLayout contentLl;

        public MyViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_ask_song);
            postUserTypeTv = $(R.id.post_user_type_tv);
            postUserTitleTv = $(R.id.post_user_title_tv);
            postUserContentTv = $(R.id.post_user_content_tv);
            postUserNameTv = $(R.id.post_user_name_tv);
            postUserHeadIv = $(R.id.post_user_head_iv);
            postUserDateTv = $(R.id.post_user_date_tv);
            postUserCommentNumTv = $(R.id.post_user_comment_num_tv);
            contentLl = $(R.id.content_ll);
            //点击内容
            contentLl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onClickContent(getAdapterPosition());
                    }
                }
            });
            //点击头像
            postUserHeadIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onClickHeadIv(getAdapterPosition());
                    }
                }
            });
        }

        @Override
        public void setData(AskSongPost data) {
            super.setData(data);
            setPostType(data);
            postUserTitleTv.setText("标题：" + data.getTitle());
            postUserContentTv.setText("描述：" + data.getContent());
            postUserNameTv.setText(data.getUser().getNickName());
            displayStrategy.displayCircle(context, data.getUser().getHeadImg(), postUserHeadIv);
            postUserDateTv.setText(data.getCreatedAt());
            postUserCommentNumTv.setText(String.valueOf(data.getCommentNum()));
        }


        private void setPostType(AskSongPost data) {
            int type = data.getType();
            switch (type) {
                case Constant.qi_ta_pu:
                    postUserTypeTv.setText("求谱类型：" + "其他类型");
                    break;
                case Constant.ji_ta_pu:
                    postUserTypeTv.setText("求谱类型：" + "吉他谱");
                    break;
                case Constant.gang_qin_pu:
                    postUserTypeTv.setText("求谱类型：" + "钢琴谱");
                    break;
                case Constant.jian_pu:
                    postUserTypeTv.setText("求谱类型：" + "简谱");
                    break;
            }
        }

    }
}
