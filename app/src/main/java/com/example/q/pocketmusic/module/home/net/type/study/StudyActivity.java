package com.example.q.pocketmusic.module.home.net.type.study;

import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.module.common.BaseActivity;

import butterknife.BindView;

public class StudyActivity extends BaseActivity<StudyPresenter.IView, StudyPresenter>
        implements StudyPresenter.IView {
    public static final String PARAM_TYPE_ID = "param_type_id";
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
