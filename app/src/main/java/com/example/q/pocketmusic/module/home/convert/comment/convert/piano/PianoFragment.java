package com.example.q.pocketmusic.module.home.convert.comment.convert.piano;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.module.common.BaseFragment;
import com.example.q.pocketmusic.util.common.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class PianoFragment extends BaseFragment<PianoFragmentPresenter.IView, PianoFragmentPresenter>
        implements PianoFragmentPresenter.IView {
    @BindView(R.id.content_et)
    EditText contentEt;
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
        contentEt.setKeyListener(null);//禁止键盘输入
    }

    @Override
    protected PianoFragmentPresenter createPresenter() {
        return new PianoFragmentPresenter(this);
    }

    @OnClick({R.id.g3, R.id.a3, R.id.b3, R.id.c4, R.id.d4, R.id.e4, R.id.f4, R.id.g4, R.id.a4, R.id.b4, R.id.c5, R.id.d5, R.id.e5, R.id.f5, R.id.g5})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            default:
                presenter.setClickScale(view.getId());
                setConvertContent();
                break;
        }
    }

    public void blank() {
        presenter.blank();
        ToastUtil.showToast("空行");
    }

    public void enter() {
        presenter.enter();
        ToastUtil.showToast("换行");
    }

    public void boLang() {
        presenter.boLang();
        ToastUtil.showToast("延长");
    }

    public void delete() {
        presenter.delete();
        ToastUtil.showToast("退格");
    }

    public void setConvertContent() {
        contentEt.setText(presenter.getConvertContent());
        contentEt.setSelection(contentEt.getText().length());//光标移动到最后一行
    }

    public String getConvertContent(){
        return presenter.getConvertContent();
    }
}