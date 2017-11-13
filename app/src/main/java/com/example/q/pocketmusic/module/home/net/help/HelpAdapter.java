package com.example.q.pocketmusic.module.home.net.help;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.util.common.LogUtils;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by 鹏君 on 2017/2/15.
 */

public class HelpAdapter extends RecyclerArrayAdapter<String> {
    public HelpAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(parent);
    }

    class MyViewHolder extends BaseViewHolder<String> {
        TextView questionTv;
        TextView answerTv;

        public MyViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_help);
            questionTv = $(R.id.question_tv);
            answerTv = $(R.id.answer_tv);
        }

        @Override
        public void setData(String data) {
            super.setData(data);
            LogUtils.e(data);
            String[] s = data.split("_");
            questionTv.setText(s[0]);
            answerTv.setText(s[1]);
        }
    }
}
