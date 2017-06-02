package com.example.q.pocketmusic.module.home.profile;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by 鹏君 on 2017/1/13.
 */

public class InstrumentAdapter extends RecyclerArrayAdapter<String> {

    public InstrumentAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(parent);
    }

    static class MyViewHolder extends BaseViewHolder<String> {
        TextView itemTv;

        public MyViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_dialog_list);
            itemTv = $(R.id.item_tv);
        }

        @Override
        public void setData(String data) {
            super.setData(data);
            itemTv.setText(data);
        }
    }
}
