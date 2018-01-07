package com.example.q.pocketmusic.module.home.local.localrecord;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.data.bean.local.RecordAudio;
import com.example.q.pocketmusic.view.widget.view.MorePopupWindow;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by 鹏君 on 2016/10/23.
 */

public class LocalRecordFragmentAdapter extends RecyclerArrayAdapter<RecordAudio> {
    private SimpleDateFormat durationFormat = new SimpleDateFormat("mm分ss秒", Locale.CHINA);
    private OnSelectListener listener;



    public interface OnSelectListener {
        void onSelectDelete(int position);
    }

    public void setListener(OnSelectListener listener) {
        this.listener = listener;
    }

    public LocalRecordFragmentAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(parent);
    }

    class MyViewHolder extends BaseViewHolder<RecordAudio> {
        TextView nameTv;
        TextView durationTv;
        ImageView moreIv;

        public MyViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_audio_record);
            nameTv = $(R.id.name_tv);
            durationTv = $(R.id.duration_tv);
            moreIv = $(R.id.more_iv);
        }

        @Override
        public void setData(RecordAudio data) {
            super.setData(data);
            nameTv.setText(data.getName());
            durationTv.setText("时长：" + durationFormat.format(new Date(data.getDuration())));
            moreIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        MorePopupWindow popupWindow=new MorePopupWindow(getContext());
                        popupWindow.addView(popupWindow.getContentLl(R.drawable.ic_vec_delete,"删除录音"));
                        popupWindow.setListener(new MorePopupWindow.OnSelectedListener() {
                            @Override
                            public void onSelected(int position) {
                                switch (position){
                                    case 0:
                                        listener.onSelectDelete(getAdapterPosition());
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
