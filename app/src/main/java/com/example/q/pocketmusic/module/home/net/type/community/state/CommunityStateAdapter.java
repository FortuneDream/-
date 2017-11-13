package com.example.q.pocketmusic.module.home.net.type.community.state;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.callback.AbsOnClickItemHeadListener;
import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.config.pic.DisplayStrategy;
import com.example.q.pocketmusic.data.bean.CommunityState;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;


public class CommunityStateAdapter extends RecyclerArrayAdapter<CommunityState> {
    private DisplayStrategy displayStrategy;
    private AbsOnClickItemHeadListener listener;

    public void setListener(AbsOnClickItemHeadListener listener) {
        this.listener = listener;
    }

    public CommunityStateAdapter(Context context) {
        super(context);
        displayStrategy = new DisplayStrategy();
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(parent);

    }

    class MyViewHolder extends BaseViewHolder<CommunityState> {
        TextView communityStateUserNameTv;
        ImageView communityStateUserHeadIv;
        TextView communityStateContentTv;

        public MyViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_community_state);
            communityStateUserNameTv = $(R.id.community_state_user_name_tv);
            communityStateUserHeadIv = $(R.id.community_state_user_head_iv);
            communityStateContentTv = $(R.id.community_state_content_tv);
        }

        @Override
        public void setData(final CommunityState data) {
            super.setData(data);
            if (data.getUser() == null) {
                return ;
            }
            communityStateUserHeadIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onClickHead(getContext(), data.getUser());
                    }
                }
            });
            displayStrategy.displayCircle(getContext(), data.getUser().getHeadImg(), communityStateUserHeadIv);
            communityStateUserNameTv.setText(data.getUser().getNickName());
            setContent(data);
        }

        private void setContent(CommunityState data) {
            Spannable spn;
            ForegroundColorSpan colorSpan;
            switch (data.getStateType()) {
                case Constant.COMMUNITY_STATE_AGREE:
                    spn = new SpannableString("点赞了：" + data.getContent());
                    colorSpan = new ForegroundColorSpan(getContext().getResources().getColor(R.color.vec_blue));
                    spn.setSpan(colorSpan, 0, 4, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    communityStateContentTv.setText(spn);
                    break;
                case Constant.COMMUNITY_STATE_COLLECTION:
                    spn = new SpannableString("收藏了：" + data.getContent());
                    colorSpan = new ForegroundColorSpan(getContext().getResources().getColor(R.color.vec_red));
                    spn.setSpan(colorSpan, 0, 4, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    communityStateContentTv.setText(spn);
                    break;
                case Constant.COMMUNITY_STATE_DOWNLOAD:
                    spn = new SpannableString("下载了：" + data.getContent());
                    colorSpan = new ForegroundColorSpan(getContext().getResources().getColor(R.color.vec_green));
                    spn.setSpan(colorSpan, 0, 4, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    communityStateContentTv.setText(spn);
                    break;
            }
        }
    }
}