package com.example.q.pocketmusic.module.home.profile.interest;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.callback.AbsOnClickItemHeadListener;
import com.example.q.pocketmusic.config.pic.GlideStrategy;
import com.example.q.pocketmusic.config.pic.IDisplayStrategy;
import com.example.q.pocketmusic.data.bean.MyUser;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by 81256 on 2017/10/31.
 */

public class UserInterestAdapter extends RecyclerArrayAdapter<MyUser> {
    private IDisplayStrategy displayStrategy;
    private AbsOnClickItemHeadListener listener;

    public UserInterestAdapter(Context context) {
        super(context);
        displayStrategy = new GlideStrategy();
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent);
    }

    class ViewHolder extends BaseViewHolder<MyUser> {
        TextView nickNameTv;
        TextView signatureTv;
        TextView coinTv;
        ImageView headIv;

        public ViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_user_interest);
            nickNameTv = $(R.id.nick_name_tv);
            signatureTv = $(R.id.signature_tv);
            coinTv = $(R.id.coin_tv);
            headIv = $(R.id.head_iv);
        }

        @Override
        public void setData(final MyUser data) {
            super.setData(data);
            displayStrategy.displayCircle(getContext(), data.getHeadImg(), headIv);
            nickNameTv.setText(data.getNickName());
            signatureTv.setText(data.getSignature());
            coinTv.setText(String.valueOf(data.getContribution()) + "枚");
        }
    }
}