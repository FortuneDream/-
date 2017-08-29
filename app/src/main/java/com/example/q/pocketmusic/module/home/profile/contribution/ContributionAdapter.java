package com.example.q.pocketmusic.module.home.profile.contribution;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.callback.AbsOnClickItemHeadListener;
import com.example.q.pocketmusic.config.pic.IDisplayStrategy;
import com.example.q.pocketmusic.model.bean.MyUser;
import com.example.q.pocketmusic.config.pic.GlideStrategy;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by 鹏君 on 2017/3/12.
 */

public class ContributionAdapter extends RecyclerArrayAdapter<MyUser> {
    private Context context;
    private IDisplayStrategy displayStrategy;
    private AbsOnClickItemHeadListener absOnClickItemHeadListener;

    public void setAbsOnClickItemHeadListener(AbsOnClickItemHeadListener absOnClickItemHeadListener) {
        this.absOnClickItemHeadListener = absOnClickItemHeadListener;
    }

    public ContributionAdapter(Context context) {
        super(context);
        this.context = context;
        displayStrategy = new GlideStrategy();
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent);
    }

    class ViewHolder extends BaseViewHolder<MyUser> {
        ImageView headIv;
        TextView nickNameTv;
        TextView rankTv;
        TextView contributionTv;
        RelativeLayout contentRl;

        public ViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_contribution);
            headIv = $(R.id.head_iv);
            rankTv = $(R.id.rank_tv);
            nickNameTv = $(R.id.nick_name_tv);
            contributionTv = $(R.id.contribution_tv);
            contentRl = $(R.id.content_rl);
            contentRl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (absOnClickItemHeadListener != null) {
                        absOnClickItemHeadListener.onClickItem(getAdapterPosition());
                    }
                }
            });
        }

        @Override
        public void setData(final MyUser data) {
            super.setData(data);
            int position = getAdapterPosition() + 1;
            rankTv.setText(position + ".");
            displayStrategy.displayCircle(context, data.getHeadImg(), headIv);
            nickNameTv.setText(data.getNickName());
            contributionTv.setText(String.valueOf(data.getActiveNum()) + " 点");
            headIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (absOnClickItemHeadListener != null) {
                        absOnClickItemHeadListener.onClickHead(getContext(), data);
                    }
                }
            });

        }
    }
}
