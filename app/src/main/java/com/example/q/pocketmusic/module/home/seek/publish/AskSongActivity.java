package com.example.q.pocketmusic.module.home.seek.publish;

import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.module.common.AuthActivity;
import com.example.q.pocketmusic.view.widget.view.TextEdit;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Cloud on 2016/11/14.
 */

public class AskSongActivity extends AuthActivity<AskSongPresenter.IView,AskSongPresenter>
        implements AskSongPresenter.IView {

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


    @Override
    public int setContentResource() {
        return R.layout.activity_ask_song;
    }


    @Override
    public void initUserView() {
        initToolbar(toolbar, "求谱信息");
    }




    @OnClick({R.id.ok_txt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ok_txt:
                String title = titleTet.getInputString();
                String content = contentTet.getInputString();
                presenter.askForSong(title, content, user);
                break;
        }
    }

    @Override
    public void setAskResult(Integer success) {
        setResult(success);
    }


    //空接口
    @Override
    public void showRefreshing(boolean isShow) {

    }

    @Override
    protected AskSongPresenter createPresenter() {
        return new AskSongPresenter(this);
    }
}