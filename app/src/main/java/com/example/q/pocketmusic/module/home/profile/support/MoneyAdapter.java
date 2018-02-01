package com.example.q.pocketmusic.module.home.profile.support;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.config.pic.DisplayStrategy;
import com.example.q.pocketmusic.data.bean.MoneySupport;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by 鹏君 on 2017/7/8.
 * （￣m￣）
 */

public class MoneyAdapter extends RecyclerArrayAdapter<MoneySupport> {
    private DisplayStrategy displayStrategy;

    public MoneyAdapter(Context context) {
        super(context);
        displayStrategy = new DisplayStrategy();
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(parent);
    }

    class MyViewHolder extends BaseViewHolder<MoneySupport> {
        ImageView headIv;
        TextView nickNameTv;
        AppCompatTextView moneyTv;
        TextView contentTv;
        TextView dateTv;

        public MyViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_money_support);
            headIv = $(R.id.head_support_iv);
            nickNameTv = $(R.id.nick_name_support_tv);
            moneyTv = $(R.id.money_tv);
            contentTv = $(R.id.content_tv);
            dateTv = $(R.id.date_tv);
        }

        @Override
        public void setData(MoneySupport data) {
            super.setData(data);
            displayStrategy.displayCircle(getContext(), data.getUser().getHeadImg(), headIv);
            nickNameTv.setText(data.getUser().getNickName());
            moneyTv.setText(String.valueOf(data.getMoney()) + "元");
            contentTv.setText(data.getContent());
            dateTv.setText(data.getCreatedAt());
        }
    }
}
