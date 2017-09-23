package com.example.q.pocketmusic.module.home.net.type.study;

import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.module.common.BaseActivity;

import butterknife.BindView;

//包含简谱，五线谱，吉他谱学习
public class StudyActivity extends BaseActivity<StudyPresenter.IView, StudyPresenter>
        implements StudyPresenter.IView {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;

    @Override
    protected StudyPresenter createPresenter() {
        return new StudyPresenter(this);
    }

    @Override
    public int setContentResource() {
        return R.layout.activity_study;
    }

    @Override
    public void initView() {
        initToolbar(toolbar,"我爱学习");
    }

}
