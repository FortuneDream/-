package com.example.q.pocketmusic.module.home.profile.convert.post;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.model.bean.convert.ConvertComment;
import com.example.q.pocketmusic.view.widget.view.MorePopupWindow;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;


public class ProfileConvertPostAdapter extends RecyclerArrayAdapter<ConvertComment> {
    private OnSelectedListener onSelectedListener;

    public void setOnSelectedListener(OnSelectedListener onSelectedListener) {
        this.onSelectedListener = onSelectedListener;
    }

    public interface OnSelectedListener {
        void onSelectedDelete(int position);
    }

    public ProfileConvertPostAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(parent);

    }

    class MyViewHolder extends BaseViewHolder<ConvertComment> {
        TextView titleTv;
        ImageView moreIv;

        public MyViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_profile_convert_post);
            titleTv = $(R.id.title_tv);
            moreIv = $(R.id.more_iv);
        }

        @Override
        public void setData(ConvertComment data) {
            super.setData(data);
            titleTv.setText(data.getPost().getTitle());
            moreIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MorePopupWindow morePopupWindow = new MorePopupWindow(getContext());
                    morePopupWindow.addView(morePopupWindow.getContentLl(R.drawable.ic_vec_delete, "删除转谱"));
                    morePopupWindow.setListener(new MorePopupWindow.OnSelectedListener() {
                        @Override
                        public void onSelected(int position) {
                            switch (position) {
                                case 0:
                                    if (onSelectedListener != null) {
                                        onSelectedListener.onSelectedDelete(getAdapterPosition());
                                    }
                                    break;
                            }
                        }
                    });
                    morePopupWindow.show(moreIv);
                }
            });
        }
    }
}