package com.example.q.pocketmusic.module.user.notify.version;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.q.pocketmusic.config.BmobInfo;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import cn.bmob.v3.Bmob;

/**
 * Created by 鹏君 on 2017/7/8.
 * （￣m￣）
 */

public class PlanAdapter extends RecyclerArrayAdapter<BmobInfo> {

    public PlanAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(parent);
    }

    class MyViewHolder extends BaseViewHolder<BmobInfo> {
        TextView planTv;

        public MyViewHolder(ViewGroup parent) {
            super(parent, android.R.layout.simple_list_item_1);
            planTv = $(android.R.id.text1);
        }

        @Override
        public void setData(BmobInfo data) {
            super.setData(data);
            int position = getAdapterPosition() + 1;
            planTv.setText(position + ".  " + data.getContent());
        }
    }
}
