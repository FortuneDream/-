package com.example.q.pocketmusic.module.home.net;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.model.bean.Song;
import com.example.q.pocketmusic.model.flag.BannerBean;
import com.example.q.pocketmusic.model.flag.ContentLL;
import com.example.q.pocketmusic.util.InstrumentFlagUtil;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.rollviewpager.RollPagerView;

/**
 * Created by 鹏君 on 2016/11/7.
 */
//暂时取消点赞功能
public class NetFragmentAdapter extends RecyclerArrayAdapter<Object> {
    public static final int BANNER = 0;
    public static final int TYPE_SONG = 1;
    public static final int RECOMMEND = 2;
    private OnOptionListener listener;

    public void setListener(OnOptionListener listener) {
        this.listener = listener;
    }

    public interface OnOptionListener {
        void onSelectType(int position);

        void onSelectRecommendSong(int position);

        void onSelectRollView(int picPosition);
    }

    public NetFragmentAdapter(Context context) {
        super(context);
    }


    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case BANNER:
                return new BannerViewHolder(parent);
            case TYPE_SONG:
                return new TypeViewHolder(parent);
        }
        return new RecommendViewHolder(parent);
    }

    @Override
    public int getViewType(int position) {
        if (getItem(position) instanceof ContentLL) {
            return TYPE_SONG;
        } else if (getItem(position) instanceof BannerBean) {
            return BANNER;
        } else {
            return RECOMMEND;
        }
    }


    //乐器list的holder
    class TypeViewHolder extends BaseViewHolder<ContentLL> implements View.OnClickListener {


        public TypeViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_combination_type_ll);
            ($(R.id.type_quanbu_tv)).setOnClickListener(this);
            ($(R.id.type_hulu_tv)).setOnClickListener(this);
            ($(R.id.type_jita_tv)).setOnClickListener(this);
            ($(R.id.type_gangqin_tv)).setOnClickListener(this);
            ($(R.id.type_sakesi_tv)).setOnClickListener(this);
            ($(R.id.type_erhu_tv)).setOnClickListener(this);
            ($(R.id.type_guzheng_tv)).setOnClickListener(this);
            ($(R.id.type_dianziqin_tv)).setOnClickListener(this);
            ($(R.id.type_pipa_tv)).setOnClickListener(this);
            ($(R.id.type_kouqin_tv)).setOnClickListener(this);
            ($(R.id.type_changdi_tv)).setOnClickListener(this);
            ($(R.id.type_dixiao_tv)).setOnClickListener(this);
            ($(R.id.type_shoufengqin_tv)).setOnClickListener(this);
            ($(R.id.type_tiqin_tv)).setOnClickListener(this);
            ($(R.id.type_tongguan_tv)).setOnClickListener(this);
            ($(R.id.type_yangqin_tv)).setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                int position = InstrumentFlagUtil.getFlag(v.getId());
                listener.onSelectType(position);
            }
        }
    }


    //广告轮播的holder
    class BannerViewHolder extends BaseViewHolder<BannerBean> {
        RollPagerView rollPagerView;

        public BannerViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_combination_banner);
            rollPagerView = $(R.id.roll_pager_view);
            rollPagerView.setAdapter(new RollPagerAdapter(rollPagerView));
            rollPagerView.setOnItemClickListener(new com.jude.rollviewpager.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    if (listener != null) {
                        listener.onSelectRollView(position);
                    }
                }
            });

        }
    }

    //热门曲谱的holder
    class RecommendViewHolder extends BaseViewHolder<Song> {
        TextView nameTv;
        TextView artistTv;
        LinearLayout contentLl;

        public RecommendViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_recommend_list);
            nameTv = $(R.id.name_tv);
            artistTv = $(R.id.artist_tv);
            contentLl = $(R.id.recommend_content);
            contentLl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onSelectRecommendSong(getAdapterPosition());
                    }
                }
            });

        }

        @Override
        public void setData(Song data) {
            super.setData(data);
            nameTv.setText(data.getName());
            if (data.getArtist() != null) {
                artistTv.setText(data.getArtist());
            } else {
                artistTv.setText("暂无");
            }
        }

    }
}
