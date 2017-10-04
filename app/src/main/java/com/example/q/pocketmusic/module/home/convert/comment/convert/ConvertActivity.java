package com.example.q.pocketmusic.module.home.convert.comment.convert;

import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.model.bean.ConvertObject;
import com.example.q.pocketmusic.module.common.AuthActivity;
import com.example.q.pocketmusic.util.common.ToastUtil;


import butterknife.BindView;
import butterknife.OnClick;

public class ConvertActivity extends AuthActivity<ConvertPresenter.IView, ConvertPresenter>
        implements ConvertPresenter.IView {
    public static final String PARAM_CONVERT_OBJECT = "param_1";
    @BindView(R.id.exit_iv)
    ImageView exitIv;
    @BindView(R.id.keep_iv)
    ImageView keepIv;
    @BindView(R.id.blank_iv)
    ImageView blankIv;
    @BindView(R.id.enter_iv)
    ImageView enterIv;
    @BindView(R.id.bo_lang_iv)
    ImageView boLangIv;
    @BindView(R.id.delete_iv)
    ImageView deleteIv;
    @BindView(R.id.content_top)
    FrameLayout contentTop;
    @BindView(R.id.content_bottom)
    FrameLayout contentBottom;

    @Override
    public int setContentResource() {
        return R.layout.activity_piano;
    }

    @Override
    public void initUserView() {
        ConvertObject object = (ConvertObject) getIntent().getSerializableExtra(PARAM_CONVERT_OBJECT);
        presenter.initFragment(getSupportFragmentManager(), object);
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


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            alertExitDialog();
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }

    //弹出退出dialog
    private void alertExitDialog() {
        new AlertDialog.Builder(getCurrentContext())
                .setTitle("提示")
                .setMessage("是否退出转谱界面?您所做的谱将不会保存哦~")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                })
                .show();
    }

    @OnClick({R.id.exit_iv, R.id.keep_iv, R.id.blank_iv, R.id.enter_iv, R.id.bo_lang_iv, R.id.delete_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.exit_iv:
                alertExitDialog();
                break;
            case R.id.keep_iv:
                alertKeepDialog();
                break;
            case R.id.blank_iv:
                presenter.blank();
                presenter.setConvertContent();
                break;
            case R.id.enter_iv:
                presenter.enter();
                presenter.setConvertContent();
                break;
            case R.id.bo_lang_iv:
                presenter.boLang();
                presenter.setConvertContent();
                break;
            case R.id.delete_iv:
                presenter.delete();
                presenter.setConvertContent();
                break;
        }

    }

    private void alertKeepDialog() {
        new AlertDialog.Builder(getCurrentContext())
                .setTitle("提示")
                .setMessage("是否保存到本地？")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ToastUtil.showToast("保存");
                    }
                })
                .show();
    }


}
