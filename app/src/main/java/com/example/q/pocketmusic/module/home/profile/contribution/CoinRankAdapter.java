package com.example.q.pocketmusic.module.home.profile.contribution;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.callback.AbsOnClickItemHeadListener;
import com.example.q.pocketmusic.config.pic.IDisplayStrategy;
import com.example.q.pocketmusic.data.bean.MyUser;
import com.example.q.pocketmusic.config.pic.GlideStrategy;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by 鹏君 on 2017/3/12.
 */

public class CoinRankAdapter extends RecyclerArrayAdapter<MyUser> {
    private IDisplayStrategy displayStrategy;
    private AbsOnClickItemHeadListener absOnClickItemHeadListener;

    public void setAbsOnClickItemHeadListener(AbsOnClickItemHeadListener absOnClickItemHeadListener) {
        this.absOnClickItemHeadListener = absOnClickItemHeadListener;
    }

    public CoinRankAdapter(Context context) {
        super(context);
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
        TextView coinTv;
        Toolbar contentToolbar;

        public ViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_coin_rank);
            headIv = $(R.id.head_iv);
            rankTv = $(R.id.rank_tv);
            nickNameTv = $(R.id.nick_name_tv);
            coinTv = $(R.id.coin_tv);
            contentToolbar = $(R.id.content_toolbar);
            contentToolbar.setOnClickListener(new View.OnClickListener() {
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
            displayStrategy.displayCircle(getContext(), data.getHeadImg(), headIv);
            nickNameTv.setText(data.getNickName());
            headIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (absOnClickItemHeadListener != null) {
                        absOnClickItemHeadListener.onClickHead(getContext(), data);
                    }
                }
            });
            setCoin(data);
        }

        //设置硬币
        private void setCoin(MyUser data) {
            Spannable spn;
            ForegroundColorSpan colorSpan;
            spn = new SpannableString(String.valueOf(data.getContribution()));
            colorSpan = new ForegroundColorSpan(getContext().getResources().getColor(R.color.vec_red));
            spn.setSpan(colorSpan, 0, spn.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            coinTv.setText(spn + "枚");
        }
    }
}
