package com.example.q.pocketmusic.module.home.profile.support;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.model.bean.MoneySupport;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by 鹏君 on 2017/7/8.
 * （￣m￣）
 */

public class SupportAdapter extends RecyclerArrayAdapter<MoneySupport> {
    public SupportAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    class MyViewHolder extends BaseViewHolder<MoneySupport> {
        ImageView headIv;
        TextView nickNameTv;
        TextView moneyTv;
        TextView contentTv;
        TextView dateTv;

        public MyViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_money_support);
            headIv = $(R.id.head_iv);
            nickNameTv = $(R.id.nick_name_tv);
            moneyTv = $(R.id.money_tv);
            contentTv = $(R.id.content_tv);
            dateTv = $(R.id.date_tv);
        }

        @Override
        public void setData(MoneySupport data) {
            super.setData(data);
            nickNameTv.setText(data.getUser().getNickName());
            moneyTv.setText(data.getMoney());
            contentTv.setText(data.getContent());
            dateTv.setText(data.getCreatedAt());
        }
    }
}
