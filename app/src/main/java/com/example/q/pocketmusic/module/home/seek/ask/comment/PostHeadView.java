package com.example.q.pocketmusic.module.home.seek.ask.comment;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.config.pic.DisplayStrategy;
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
    private String date;
    private String content;
    private String name;
    private String title;
    private String headUrl;

    public PostHeadView(Context context, String content, String name, String title, String headUrl, String date) {
        this.context = context;
        this.content = content;
        this.name = name;
        this.title = title;
        this.headUrl = headUrl;
        this.date = date;
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


        postUserContentTv.setText(content);
        postUserNameTv.setText(name);
        postUserTitleTv.setText("所求曲谱："+title);
        postUserDateTv.setText(date);
        new DisplayStrategy().displayCircle(context, headUrl, postUserHeadIv);
    }
}
