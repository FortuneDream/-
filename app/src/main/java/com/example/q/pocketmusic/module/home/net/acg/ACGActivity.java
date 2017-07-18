package com.example.q.pocketmusic.module.home.net.acg;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.module.common.BaseActivity;

public class ACGActivity extends BaseActivity<ACGPresenter.IView,ACGPresenter>
implements  ACGPresenter.IView{

    @Override
    protected ACGPresenter createPresenter() {
        return new ACGPresenter(this);
    }



    @Override
    public int setContentResource() {
        return R.layout.activity_acg;
    }

    @Override
    public void initView() {

    }
}
