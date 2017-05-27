package com.example.q.pocketmusic.module.home.seek.publish;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.module.common.AuthActivity;
import com.example.q.pocketmusic.util.LogUtils;
import com.example.q.pocketmusic.view.widget.view.TextEdit;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Cloud on 2016/11/14.
 */

public class AskSongActivity extends AuthActivity<AskSongPresenter.IView, AskSongPresenter>
        implements AskSongPresenter.IView, TagFlowLayout.OnSelectListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.title_tet)
    TextEdit titleTet;
    @BindView(R.id.content_tet)
    TextEdit contentTet;
    @BindView(R.id.ok_txt)
    TextView okTxt;
    public static final int REQUEST_ASK = 1001;//跳转到求谱界面
    @BindView(R.id.tag_flow_layout)
    TagFlowLayout tagFlowLayout;


    @Override
    public int setContentResource() {
        return R.layout.activity_ask_song;
    }


    @Override
    public void initUserView() {
        initToolbar(toolbar, "发布求谱信息");
        //初始化flowLayout
        List<String> list = new ArrayList<>();
        list.add("吉他谱");
        list.add("简谱");
        list.add("钢琴谱");
        list.add("其他类型");
        tagFlowLayout.setAdapter(new AskTagAdapter(list, getCurrentContext()));
        tagFlowLayout.setOnSelectListener(this);
    }

    //Tag选中
    @Override
    public void onSelected(Set<Integer> selectPosSet) {
        presenter.setSelectedTag(selectPosSet);
    }


    @OnClick({R.id.ok_txt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ok_txt:
                String title = titleTet.getInputString();
                String content = contentTet.getInputString();
                presenter.askForSong(title, content,user);
                break;
        }
    }

    @Override
    public void setAskResult(Integer success) {
        setResult(success);
    }


    @Override
    protected AskSongPresenter createPresenter() {
        return new AskSongPresenter(this);
    }


}