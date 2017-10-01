package com.example.q.pocketmusic.module.home.convert.convert.piano;


import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.module.common.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

public class PianoFragment extends BaseFragment<PianoFragmentPresenter.IView, PianoFragmentPresenter>
        implements PianoFragmentPresenter.IView, View.OnTouchListener {
    @BindView(R.id.title_edt)
    EditText titleEdt;
    @BindView(R.id.content_et)
    EditText contentEt;
    @BindView(R.id.keep_iv)
    ImageView keepIv;
    @BindView(R.id.tab_iv)
    ImageView tabIv;
    @BindView(R.id.enter_iv)
    ImageView enterIv;
    @BindView(R.id.bo_lang_iv)
    ImageView boLangIv;
    @BindView(R.id.back_iv)
    ImageView backIv;
    @BindView(R.id.parent)
    LinearLayout parent;
    @BindView(R.id.g3)
    Button g3;
    @BindView(R.id.a3)
    Button a3;
    @BindView(R.id.b3)
    Button b3;
    @BindView(R.id.c4)
    Button c4;
    @BindView(R.id.d4)
    Button d4;
    @BindView(R.id.e4)
    Button e4;
    @BindView(R.id.f4)
    Button f4;
    @BindView(R.id.g4)
    Button g4;
    @BindView(R.id.a4)
    Button a4;
    @BindView(R.id.b4)
    Button b4;
    @BindView(R.id.c5)
    Button c5;
    @BindView(R.id.d5)
    Button d5;
    @BindView(R.id.e5)
    Button e5;
    @BindView(R.id.f5)
    Button f5;
    @BindView(R.id.g5)
    Button g5;

    @Override
    public int setContentResource() {
        return R.layout.fragment_piano;
    }

    @Override
    public void initView() {
        backIv.setOnTouchListener(this);
    }


    @Override
    protected PianoFragmentPresenter createPresenter() {
        return new PianoFragmentPresenter(this);
    }


    @OnClick({R.id.tab_iv, R.id.enter_iv, R.id.bo_lang_iv,  R.id.g3, R.id.a3, R.id.b3, R.id.c4, R.id.d4, R.id.e4, R.id.f4, R.id.g4, R.id.a4, R.id.b4, R.id.c5, R.id.d5, R.id.e5, R.id.f5, R.id.g5})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tab_iv:
                presenter.setTab();//空行
                break;
            case R.id.enter_iv:
                presenter.setEnter();//换行
                break;
            case R.id.bo_lang_iv:
                presenter.setBoLang();//波浪
                break;
            case R.id.keep_iv:
                presenter.keepPic("");
                break;
            default:
                presenter.setClickScale(view.getId());
                break;
        }
    }

    @Override
    public void back() {
        contentEt.setText(presenter.setBack());
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
}