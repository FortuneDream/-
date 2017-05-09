package com.example.q.pocketmusic.module.piano;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.module.common.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PianoActivity extends BaseActivity<PianoPresenter.IView,PianoPresenter>
        implements PianoPresenter.IView, View.OnTouchListener {
    @BindView(R.id.content_tv)
    TextView contentTv;
    @BindView(R.id.do_1)
    Button do1;
    @BindView(R.id.re_2)
    Button re2;
    @BindView(R.id.mi_3)
    Button mi3;
    @BindView(R.id.fa_4)
    Button fa4;
    @BindView(R.id.sol_5)
    Button sol5;
    @BindView(R.id.la_6)
    Button la6;
    @BindView(R.id.xi_7)
    Button xi7;
    @BindView(R.id.parent)
    LinearLayout parent;
    @BindView(R.id.back_iv)
    ImageView backIv;
    @BindView(R.id.tab_iv)
    ImageView tabIv;
    @BindView(R.id.enter_iv)
    ImageView enterIv;
    @BindView(R.id.bo_lang_iv)
    ImageView boLangIv;
    @BindView(R.id.title_edt)
    EditText titleEdt;
    @BindView(R.id.keep_iv)
    ImageView keepIv;

    @Override
    public int setContentResource() {
        return R.layout.activity_piano;
    }

    @Override
    public void initView() {
        backIv.setOnTouchListener(this);
    }

    //强制横屏
    @Override
    protected void onResume() {
        super.onResume();
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }




    @OnClick({R.id.back_iv, R.id.tab_iv, R.id.enter_iv, R.id.bo_lang_iv, R.id.do_1, R.id.re_2, R.id.mi_3, R.id.fa_4, R.id.sol_5, R.id.la_6, R.id.xi_7,R.id.keep_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.keep_iv://保存
                String title=titleEdt.getText().toString();
                presenter.keepPic(title);
                break;
            case R.id.back_iv:
                back();
                break;
            case R.id.tab_iv:
                contentTv.setText(presenter.setTab());
                break;
            case R.id.enter_iv:
                contentTv.setText(presenter.setEnter());
                break;
            case R.id.bo_lang_iv:
                contentTv.setText(presenter.setBoLang());
                break;
            case R.id.do_1:
                contentTv.setText(presenter.sound(view.getId()));
                break;
            case R.id.re_2:
                contentTv.setText(presenter.sound(view.getId()));
                break;
            case R.id.mi_3:
                contentTv.setText(presenter.sound(view.getId()));
                break;
            case R.id.fa_4:
                contentTv.setText(presenter.sound(view.getId()));
                break;
            case R.id.sol_5:
                contentTv.setText(presenter.sound(view.getId()));
                break;
            case R.id.la_6:
                contentTv.setText(presenter.sound(view.getId()));
                break;
            case R.id.xi_7:
                contentTv.setText(presenter.sound(view.getId()));
                break;
        }
    }

    @Override
    public void back() {
        contentTv.setText(presenter.setBack());
    }


    //快速删除
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.back_iv:
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        presenter.openQuickBack();
                        break;
                    case MotionEvent.ACTION_UP:
                        presenter.closeQuickBack();
                        break;
                }
                break;
        }
        return true;
    }

    @Override
    protected PianoPresenter createPresenter() {
        return new PianoPresenter(this);
    }


    @OnClick(R.id.keep_iv)
    public void onViewClicked() {
    }
}
