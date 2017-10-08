package com.example.q.pocketmusic.module.home.net.type.community.ask.publish;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.model.bean.MyUser;
import com.example.q.pocketmusic.module.common.AuthActivity;
import com.example.q.pocketmusic.util.UserUtil;
import com.example.q.pocketmusic.view.dialog.CoinDialogBuilder;
import com.example.q.pocketmusic.view.widget.view.TextEdit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 鹏君 on 2016/11/14.
 */

public class PublishAskActivity extends AuthActivity<PublishSongPresenter.IView, PublishSongPresenter>
        implements PublishSongPresenter.IView {
    public static final String PARAM_TYPE_ID = "param_1";
    public static final int REQUEST_ASK = 1001;//跳转到求谱界面
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.title_tet)
    TextEdit titleTet;
    @BindView(R.id.content_tet)
    TextEdit contentTet;
    @BindView(R.id.add_index_iv)
    ImageView addIndexIv;
    @BindView(R.id.index_tv)
    TextView indexTv;
    @BindView(R.id.reduce_index_iv)
    ImageView reduceIndexIv;
    @BindView(R.id.ok_txt)
    TextView okTxt;


    @Override
    public int setContentResource() {
        return R.layout.activity_publish_ask;
    }


    @Override
    public void initUserView() {
        initToolbar(toolbar, "发布求谱信息");
        presenter.setIndex(0);
        presenter.setTypeId(getIntent().getIntExtra(PARAM_TYPE_ID, 0));
    }


    @OnClick({R.id.ok_txt, R.id.index_tv, R.id.reduce_index_iv, R.id.add_index_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ok_txt:
                String title = titleTet.getInputString();
                String content = contentTet.getInputString();
                presenter.checkAsk(title, content, UserUtil.user);
                break;
            case R.id.add_index_iv:
                presenter.addIndex();
                break;
            case R.id.reduce_index_iv:
                presenter.reduceIndex();
                break;
        }
    }

    //消耗硬币确认
    @Override
    public void alertCoinDialog(int coin, final String title, final String content, final MyUser user) {
        new CoinDialogBuilder(this, coin)
                .setPositiveButton(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.askForSong(title, content, user);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    @Override
    public void changeIndex(int index) {
        indexTv.setText(String.valueOf(index));
    }

    @Override
    public void setAskResult(Integer success) {
        setResult(success);
    }


    @Override
    protected PublishSongPresenter createPresenter() {
        return new PublishSongPresenter(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}