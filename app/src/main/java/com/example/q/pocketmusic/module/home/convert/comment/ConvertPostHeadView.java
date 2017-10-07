package com.example.q.pocketmusic.module.home.convert.comment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.config.pic.DisplayStrategy;
import com.example.q.pocketmusic.module.home.net.type.community.ask.comment.AskPostHeadView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by 81256 on 2017/10/4.
 */

public class ConvertPostHeadView implements RecyclerArrayAdapter.ItemView {
    private TextView convertPostUserContentTv;
    private TextView convertPostUserNameTv;
    private TextView convertPostUserTitleTv;
    private ImageView convertPostUserHeadIv;
    private TextView convertPostUserDateTv;
    private TextView convertCoinTv;
    private String date;
    private String content;
    private String name;
    private String title;
    private String headUrl;
    private int coin;

    public ConvertPostHeadView(String date, String content, String name, String title, String headUrl, int coin) {
        this.date = date;
        this.content = content;
        this.name = name;
        this.title = title;
        this.headUrl = headUrl;
        this.coin = coin;
    }

    @Override
    public View onCreateView(ViewGroup parent) {
        return View.inflate(parent.getContext(), R.layout.head_convert_post_view, null);
    }

    @Override
    public void onBindView(View headerView) {
        convertPostUserContentTv = (TextView) headerView.findViewById(R.id.convert_post_user_content_tv);
        convertPostUserNameTv = (TextView) headerView.findViewById(R.id.convert_post_user_name_tv);
        convertPostUserTitleTv = (TextView) headerView.findViewById(R.id.convert_post_user_title_tv);
        convertPostUserHeadIv = (ImageView) headerView.findViewById(R.id.convert_post_user_head_iv);
        convertPostUserDateTv = (TextView) headerView.findViewById(R.id.convert_post_user_date_tv);

        convertCoinTv = (TextView) headerView.findViewById(R.id.convert_coin_tv);

        convertPostUserContentTv.setText(content);
        convertPostUserNameTv.setText(name);
        convertPostUserTitleTv.setText(title);
        convertCoinTv.setText(String.valueOf(String.valueOf(coin)));
        new DisplayStrategy().displayCircle(headerView.getContext(), headUrl, convertPostUserHeadIv);
        convertPostUserDateTv.setText(date);

    }
}
