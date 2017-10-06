package com.example.q.pocketmusic.module.home.profile.convert.post;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.model.bean.convert.ConvertComment;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;


public class ProfileConvertPostAdapter extends RecyclerArrayAdapter<ConvertComment> {


    public ProfileConvertPostAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(parent);

    }

    class MyViewHolder extends BaseViewHolder<ConvertComment> {
        TextView nameTv;

        public MyViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_profile_convert_post);
            nameTv = $(R.id.name_tv);
        }

        @Override
        public void setData(ConvertComment data) {
            super.setData(data);
            nameTv.setText(data.getPost().getTitle());
        }
    }
}