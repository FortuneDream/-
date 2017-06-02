package com.example.q.pocketmusic.module.user.notify.suggestion;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.model.bean.bmob.UserSuggestion;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by 鹏君 on 2016/11/15.
 */

public class SuggestionAdapter extends RecyclerArrayAdapter<UserSuggestion> {
    public SuggestionAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(parent);
    }

    class MyViewHolder extends BaseViewHolder<UserSuggestion> {
        TextView suggestionTv;
        TextView replyTv;

        public MyViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_suggestion);
            suggestionTv = $(R.id.suggestion_tv);
            replyTv = $(R.id.reply_tv);
        }

        @Override
        public void setData(UserSuggestion data) {
            super.setData(data);
            suggestionTv.setText("我: "+data.getSuggestion());
            if (data.getReply() == null) {
                replyTv.setText("管理员：暂无");
            } else {
                replyTv.setText("管理员:"+data.getReply());
            }

        }

    }
}
