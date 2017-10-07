package com.example.q.pocketmusic.module.home.convert.comment.convert;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;


import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.ConvertObject;
import com.example.q.pocketmusic.model.bean.convert.ConvertPost;
import com.example.q.pocketmusic.module.common.AuthActivity;
import com.example.q.pocketmusic.view.dialog.EditDialog;


import butterknife.BindView;
import butterknife.OnClick;

public class ConvertActivity extends AuthActivity<ConvertPresenter.IView, ConvertPresenter>
        implements ConvertPresenter.IView {
    public static final String PARAM_CONVERT_OBJECT = "param_1";
    public static final String PARAM_CONVERT_POST = "param_2";//如果来自网络，则属于某个转谱贴
    public static final String RESULT_PARAM_TITLE="result_1";
    public static final String RESULT_PARAM_CONTENT="result_2";
    @BindView(R.id.exit_iv)
    ImageView exitIv;
    @BindView(R.id.ok_iv)
    ImageView okIv;
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
        return R.layout.activity_convert;
    }

    @Override
    public void initUserView() {
        ConvertObject object = (ConvertObject) getIntent().getSerializableExtra(PARAM_CONVERT_OBJECT);
        if (object.getLoadingWay() == Constant.NET) {
            presenter.setPost((ConvertPost) getIntent().getSerializableExtra(PARAM_CONVERT_POST));
        }//来自网络
        presenter.setConvertObject(object);
        presenter.initFragment(getSupportFragmentManager(), object);
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
                .setMessage("是否退出转谱界面?您所做的谱将自动保存")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        presenter.keepConvertSong();
                    }
                })
                .setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    @OnClick({R.id.exit_iv, R.id.ok_iv, R.id.blank_iv, R.id.enter_iv, R.id.bo_lang_iv, R.id.delete_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.exit_iv:
                alertExitDialog();//退出，提示保存在本地
                break;
            case R.id.ok_iv:
                alertSendCommentDialog();//发送转谱
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

    //弹出发送评论dialog
    private void alertSendCommentDialog() {
        new EditDialog.Builder(getCurrentContext())
                .setHint("请输入保存曲谱名")
                .setTitle("保存")
                .setListener(new EditDialog.Builder.OnSelectedListener() {
                    @Override
                    public void onSelectedOk(String str) {
                        setActivityResult(str,presenter.getConvertContent());
                    }



                    @Override
                    public void onSelectedCancel() {

                    }
                }).create().show();


    }

    private void setActivityResult(String str, String convertContent) {
        Intent intent=new Intent();
        intent.putExtra(RESULT_PARAM_CONTENT,convertContent);
        intent.putExtra(RESULT_PARAM_TITLE,str);
        setResult(RESULT_OK,intent);
        finish();
    }


}
