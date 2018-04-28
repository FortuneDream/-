package com.example.q.pocketmusic.module.home.profile.share;

import android.content.Context;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.config.constant.InstrumentConstant;
import com.example.q.pocketmusic.data.bean.share.ShareSong;
import com.example.q.pocketmusic.view.widget.view.MorePopupWindow;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by 鹏君 on 2017/5/26.
 */

public class UserShareAdapter extends RecyclerArrayAdapter<ShareSong> {
    private OnSelectListener onSelectListener;

    public UserShareAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(parent);
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        this.onSelectListener = onSelectListener;
    }

    public interface OnSelectListener {
        void onSelectDelete(int position);

        void onSelectItem(int position);

        void onSelectModify(int position);

        void onSelectChangeType(int position);

    }

    class MyViewHolder extends BaseViewHolder<ShareSong> {
        RelativeLayout contentRl;
        TextView nameTv;
        TextView contentTv;
        TextView instrumentTv;
        ImageView moreIv;

        public MyViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_user_share);
            contentRl = $(R.id.content_rl);
            instrumentTv = $(R.id.instrument_tv);
            nameTv = $(R.id.name_tv);
            contentTv = $(R.id.content_tv);
            moreIv = $(R.id.more_iv);
        }

        @Override
        public void setData(ShareSong data) {
            super.setData(data);
            nameTv.setText(data.getName());
            contentTv.setText(data.getContent());
            instrumentTv.setText(InstrumentConstant.getTypeName(data.getInstrument()));
            contentRl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onSelectListener != null) {
                        onSelectListener.onSelectItem(getAdapterPosition());
                    }
                }
            });
            moreIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onSelectListener != null) {
                        final MorePopupWindow popupWindow = new MorePopupWindow(getContext());
                        popupWindow.addView(popupWindow.getContentLl(R.drawable.ic_vec_delete, "删除曲谱"));
                        popupWindow.addView(popupWindow.getContentLl(R.drawable.ic_vec_modify, "修改名字"));
                        popupWindow.addView(popupWindow.getContentLl(R.drawable.ic_vec_change_type, "修改类型"));
                        popupWindow.setListener(new MorePopupWindow.OnSelectedListener() {
                            @Override
                            public void onSelected(int position) {
                                switch (position) {
                                    case 0:
                                        onSelectListener.onSelectDelete(getAdapterPosition());
                                        break;
                                    case 1:
                                        onSelectListener.onSelectModify(getAdapterPosition());
                                        break;
                                    case 2:
                                        onSelectListener.onSelectChangeType(getAdapterPosition());
                                        break;
                                }
                            }
                        });
                        popupWindow.show(moreIv);
                    }
                }
            });
        }
    }
}
