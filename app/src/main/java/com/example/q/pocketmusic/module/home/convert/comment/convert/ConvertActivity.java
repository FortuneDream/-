package com.example.q.pocketmusic.module.home.convert.comment.convert;

import android.content.pm.ActivityInfo;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.module.common.BaseActivity;

public class ConvertActivity extends BaseActivity<ConvertPresenter.IView, ConvertPresenter>
        implements ConvertPresenter.IView {



    @Override
    public int setContentResource() {
        return R.layout.activity_piano;
    }

    @Override
    public void initView() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }



    @Override
    protected ConvertPresenter createPresenter() {
        return new ConvertPresenter(this);
    }



}
