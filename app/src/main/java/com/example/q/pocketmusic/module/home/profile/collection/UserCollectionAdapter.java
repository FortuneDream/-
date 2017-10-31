package com.example.q.pocketmusic.module.home.profile.collection;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.model.bean.collection.CollectionSong;
import com.example.q.pocketmusic.util.common.LogUtils;
import com.example.q.pocketmusic.view.widget.view.MorePopupWindow;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by 鹏君 on 2016/11/15.
 */

public class UserCollectionAdapter extends RecyclerArrayAdapter<CollectionSong> {
    public UserCollectionAdapter(Context context) {
        super(context);
    }

    private OnSelectListener listener;

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        this.listener = onSelectListener;
    }

    public interface OnSelectListener {
        void onSelectDelete(int position);

        void onSelectItem(int position);

        void onSelectModify(int position);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(parent);
    }

    class MyViewHolder extends BaseViewHolder<CollectionSong> {
        TextView nameTv;
        TextView contentTv;
        ImageView moreIv;
        RelativeLayout contentRl;

        public MyViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_collection);
            nameTv = $(R.id.name_tv);
            contentTv = $(R.id.content_tv);
            moreIv = $(R.id.more_iv);
            contentRl = $(R.id.content_rl);
        }

        @Override
        public void setData(CollectionSong data) {
            super.setData(data);
            nameTv.setText("曲谱名：" + data.getName());
            if (data.getContent() == null) {
                contentTv.setText("描述：无");
            } else {
                contentTv.setText("描述：" + data.getContent());
            }

            contentRl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onSelectItem(getAdapterPosition());
                    }
                }
            });
            moreIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        final MorePopupWindow popupWindow = new MorePopupWindow(getContext());
                        popupWindow.addView(popupWindow.getContentLl(R.drawable.ic_vec_delete, "删除曲谱"));
                        popupWindow.addView(popupWindow.getContentLl(R.drawable.ic_vec_modify, "修改名字"));
                        popupWindow.setListener(new MorePopupWindow.OnSelectedListener() {
                            @Override
                            public void onSelected(int position) {
                                switch (position) {
                                    case 0:
                                        listener.onSelectDelete(getAdapterPosition());
                                        break;
                                    case 1:
                                        listener.onSelectModify(getAdapterPosition());
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
