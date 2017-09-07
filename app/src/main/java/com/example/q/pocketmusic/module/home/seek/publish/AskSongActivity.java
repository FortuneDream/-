package com.example.q.pocketmusic.module.home.seek.publish;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.MyUser;
import com.example.q.pocketmusic.module.common.AuthActivity;
import com.example.q.pocketmusic.view.dialog.CoinDialogBuilder;
import com.example.q.pocketmusic.view.widget.view.TextEdit;
import com.google.android.flexbox.FlexboxLayout;

import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 鹏君 on 2016/11/14.
 */

public class AskSongActivity extends AuthActivity<AskSongPresenter.IView, AskSongPresenter>
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
    @BindView(R.id.add_index_iv)
    ImageView addIndexIv;
    @BindView(R.id.index_tv)
    TextView indexTv;
    @BindView(R.id.reduce_index_iv)
    ImageView reduceIndexIv;
    @BindView(R.id.jita_tv)
    TextView jitaTv;
    @BindView(R.id.jianpu_tv)
    TextView jianpuTv;
    @BindView(R.id.gangqin_tv)
    TextView gangqinTv;
    @BindView(R.id.qita_tv)
    TextView qitaTv;
    @BindView(R.id.flex_box_layout)
    FlexboxLayout flexBoxLayout;


    @Override
    public int setContentResource() {
        return R.layout.activity_ask_song;
    }


    @Override
    public void initUserView() {
        initToolbar(toolbar, "发布求谱信息");
        presenter.setIndex(0);

    }

    @OnClick({R.id.jita_tv, R.id.jianpu_tv, R.id.gangqin_tv, R.id.qita_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.jita_tv:
                setTag(Constant.ji_ta_pu);
                break;
            case R.id.jianpu_tv:
                setTag(Constant.jian_pu);
                break;
            case R.id.gangqin_tv:
                setTag(Constant.gang_qin_pu);
                break;
            case R.id.qita_tv:
                setTag(Constant.qi_ta_pu);
                break;
        }
    }

    private void setTag(int tagIndex) {
        for (int i=0;i<flexBoxLayout.getFlexItemCount();i++){
            if (i==tagIndex){
                TextView view=(TextView)(flexBoxLayout.getFlexItemAt(i));
                view.setTextColor(getResources().getColor(R.color.white));
                view.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            }else {
                TextView view=(TextView)(flexBoxLayout.getFlexItemAt(i));
                view.setTextColor(getResources().getColor(R.color.text));
                view.setBackgroundColor(getResources().getColor(R.color.white));
            }
        }
        presenter.setSelectedTag(tagIndex);
    }


    @OnClick({R.id.ok_txt, R.id.index_tv, R.id.reduce_index_iv, R.id.add_index_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ok_txt:
                String title = titleTet.getInputString();
                String content = contentTet.getInputString();
                presenter.checkAsk(title, content, user);
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
    protected AskSongPresenter createPresenter() {
        return new AskSongPresenter(this);
    }




}