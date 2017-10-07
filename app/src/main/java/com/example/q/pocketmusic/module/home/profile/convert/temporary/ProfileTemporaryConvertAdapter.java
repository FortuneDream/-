package com.example.q.pocketmusic.module.home.profile.convert.temporary;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.model.bean.convert.ConvertSong;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;


public class ProfileTemporaryConvertAdapter extends RecyclerArrayAdapter<ConvertSong> {
    private OnSelectListener onSelectListener;

    public interface OnSelectListener {
        void onSelectMore(int position);
    }

    public OnSelectListener getOnSelectListener() {
        return onSelectListener;
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        this.onSelectListener = onSelectListener;
    }

    public ProfileTemporaryConvertAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(parent);

    }

    class MyViewHolder extends BaseViewHolder<ConvertSong> {
        private TextView nameTv;
        private AppCompatImageView moreIv;

        public MyViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_convert_adapter);
            nameTv = $(R.id.name_tv);
            moreIv = $(R.id.more_iv);
        }

        @Override
        public void setData(ConvertSong data) {
            super.setData(data);
            nameTv.setText(data.getName());
            moreIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onSelectListener != null) {
                        onSelectListener.onSelectMore(getAdapterPosition());
                    }
                }
            });
        }
    }
}