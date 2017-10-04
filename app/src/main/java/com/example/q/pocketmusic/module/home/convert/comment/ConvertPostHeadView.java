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
    private ImageView convertPostCheckPic;

    private String date;
    private String content;
    private String name;
    private String title;
    private String headUrl;

    private AskPostHeadView.OnClickIndexListener onClickIndexListener;

    public interface OnClickCheckPicListener {
        void onClickCheckPic();
    }

    public void setOnClickIndexListener(AskPostHeadView.OnClickIndexListener onClickIndexListener) {
        this.onClickIndexListener = onClickIndexListener;
    }

    public ConvertPostHeadView(String date, String content, String name, String title, String headUrl) {
        this.date = date;
        this.content = content;
        this.name = name;
        this.title = title;
        this.headUrl = headUrl;
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
        convertPostCheckPic = (ImageView) headerView.findViewById(R.id.convert_post_check_pic);

        convertPostUserContentTv.setText(content);
        convertPostUserNameTv.setText(name);
        convertPostUserTitleTv.setText(title);
        new DisplayStrategy().displayCircle(headerView.getContext(), headUrl, convertPostUserHeadIv);
        convertPostUserDateTv.setText(date);
        convertPostCheckPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickIndexListener != null) {
                    onClickIndexListener.onClick();
                }
            }
        });
    }
}
