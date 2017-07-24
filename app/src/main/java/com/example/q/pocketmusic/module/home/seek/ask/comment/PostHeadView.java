package com.example.q.pocketmusic.module.home.seek.ask.comment;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.config.pic.DisplayStrategy;
import com.example.q.pocketmusic.util.common.LogUtils;
import com.example.q.pocketmusic.view.dialog.CoinDialogBuilder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by 鹏君 on 2017/1/22.
 */

public class PostHeadView implements RecyclerArrayAdapter.ItemView {
    private Context context;
    private TextView postUserContentTv;
    private TextView postUserNameTv;
    private TextView postUserTitleTv;
    private ImageView postUserHeadIv;
    private TextView postUserDateTv;
    private ImageView postUserAddIndexIv;
    private String date;
    private String content;
    private String name;
    private String title;
    private String headUrl;
    private boolean isFromUser;
    private OnClickIndexListener onClickIndexListener;

    public interface OnClickIndexListener {
        void onClick();
    }

    public PostHeadView(Context context, String content, String name, String title, String headUrl, String date, Boolean isFromUser) {
        this.context = context;
        this.content = content;
        this.name = name;
        this.title = title;
        this.headUrl = headUrl;
        this.date = date;
        this.isFromUser = isFromUser;
    }

    public void setOnClickIndexListener(OnClickIndexListener onClickIndexListener) {
        this.onClickIndexListener = onClickIndexListener;
    }

    @Override
    public View onCreateView(ViewGroup parent) {
        return View.inflate(context, R.layout.head_post_view, null);
    }

    @Override
    public void onBindView(View headerView) {
        postUserContentTv = (TextView) headerView.findViewById(R.id.post_user_content_tv);
        postUserNameTv = (TextView) headerView.findViewById(R.id.post_user_name_tv);
        postUserTitleTv = (TextView) headerView.findViewById(R.id.post_user_title_tv);
        postUserHeadIv = (ImageView) headerView.findViewById(R.id.post_user_head_iv);
        postUserDateTv = (TextView) headerView.findViewById(R.id.post_user_date_tv);
        postUserAddIndexIv = (ImageView) headerView.findViewById(R.id.post_add_index_iv);

        postUserContentTv.setText(content);
        postUserNameTv.setText(name);
        postUserTitleTv.setText("所求曲谱：" + title);
        postUserDateTv.setText(date);
        new DisplayStrategy().displayCircle(context, headUrl, postUserHeadIv);

        setAddIndexIvInit();
    }

    private void setAddIndexIvInit() {
        if (isFromUser) {
            postUserAddIndexIv.setVisibility(View.VISIBLE);
            postUserAddIndexIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickIndexListener != null) {
                        onClickIndexListener.onClick();
                    }
                }
            });
            Drawable drawable = postUserAddIndexIv.getDrawable();
            if (drawable instanceof Animatable) {
                ((Animatable) drawable).start();
            }
        } else {
            postUserAddIndexIv.setVisibility(View.GONE);
        }
    }
}
