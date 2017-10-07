package com.example.q.pocketmusic.module.home.profile.convert.temporary;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.model.bean.convert.ConvertSong;
import com.example.q.pocketmusic.view.widget.view.MorePopupWindow;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;


public class ProfileTemporaryConvertAdapter extends RecyclerArrayAdapter<ConvertSong> {
    private OnSelectListener onSelectListener;

    public interface OnSelectListener {
        void onSelectDelete(int position);
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
        private TextView titleTv;
        private AppCompatImageView moreIv;

        public MyViewHolder(final ViewGroup parent) {
            super(parent, R.layout.item_convert_adapter);
            titleTv = $(R.id.title_tv);
            moreIv = $(R.id.more_iv);

        }

        @Override
        public void setData(ConvertSong data) {
            super.setData(data);
            titleTv.setText(data.getName());
            moreIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MorePopupWindow popupWindow = new MorePopupWindow(getContext());
                    popupWindow.addView(popupWindow.getContentLl(R.drawable.ic_vec_delete, "删除转谱"));
                    popupWindow.setListener(new MorePopupWindow.OnSelectedListener() {
                        @Override
                        public void onSelected(int position) {
                            switch (position) {
                                case 0:
                                    if (onSelectListener != null) {
                                        onSelectListener.onSelectDelete(getAdapterPosition());
                                    }
                                    break;
                            }

                        }
                    });
                    popupWindow.show(moreIv);
                }
            });
        }
    }
}