package com.example.q.pocketmusic.module.home.net.banner;

import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.module.common.BaseActivity;

import butterknife.BindView;

public class BannerActivity extends BaseActivity<BannerPresenter.IView,BannerPresenter>
        implements BannerPresenter.IView {
    public static final String PARAM_PIC_POSITION = "pic_position";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    private int position;

    @Override
    public int setContentResource() {
        return R.layout.activity_banner;
    }

    @Override
    public void initView() {
        position = getIntent().getIntExtra(PARAM_PIC_POSITION, 0);
        initToolbar(toolbar, getTitle(position));
    }


    private String getTitle(int position) {
        String title;
        if (position == 0) {
            title = "新功能介绍";
        } else if (position == 1) {
            title = "Bug修复";
        } else {
            title = "其他";
        }
        return title;
    }

    @Override
    public void showRefreshing(boolean isShow) {

    }

    @Override
    protected BannerPresenter createPresenter() {
        return new BannerPresenter(this);
    }
}
