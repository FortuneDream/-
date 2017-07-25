package com.example.q.pocketmusic.module.user.other.share;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.model.bean.share.ShareSong;
import com.example.q.pocketmusic.model.flag.Text;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by 鹏君 on 2017/7/24.
 * （￣m￣）
 */

public class OtherShareAdapter extends RecyclerArrayAdapter<ShareSong> {
    public OtherShareAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent);
    }



    class ViewHolder extends BaseViewHolder<ShareSong> {
        TextView nameTv;
        TextView contentTv;

        public ViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_other_share);
            nameTv = $(R.id.share_name_tv);
            contentTv = $(R.id.share_content_tv);
        }

        @Override
        public void setData(ShareSong data) {
            super.setData(data);
            nameTv.setText(data.getName());
            contentTv.setText(data.getContent());
        }
    }
}
