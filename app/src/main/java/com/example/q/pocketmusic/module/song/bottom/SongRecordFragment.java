package com.example.q.pocketmusic.module.song.bottom;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.module.common.BaseFragment;
import com.example.q.pocketmusic.util.common.ToastUtil;
import com.example.q.pocketmusic.view.dialog.EditDialog;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 鹏君 on 2017/5/31.
 */

public class SongRecordFragment extends BaseFragment<SongRecordPresenter.IView, SongRecordPresenter>
        implements SongRecordPresenter.IView {
    @BindView(R.id.record_play_iv)
    AppCompatImageView recordPlayIv;
    @BindView(R.id.time_tv)
    TextView timeTv;
    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;
    @BindView(R.id.record_rl)
    RelativeLayout recordRl;
    private EditDialog editDialog;//编辑框
    private final static String PARAM_Intent = "intent";


    public static SongRecordFragment newInstance(Intent intent) {
        SongRecordFragment songRecordFragment = new SongRecordFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(PARAM_Intent, intent);
        songRecordFragment.setArguments(bundle);
        return songRecordFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.setIntent((Intent) getArguments().getParcelable(PARAM_Intent));
    }

    @Override
    public int setContentResource() {
        return R.layout.fragment_song_record;
    }

    @Override
    public void initView() {
        presenter.init();
    }

    @Override
    protected SongRecordPresenter createPresenter() {
        return new SongRecordPresenter(this);
    }


    @OnClick(R.id.record_play_iv)
    public void onViewClicked() {
        presenter.record();
    }

    //改变状态按钮图标
    @Override
    public void setBtnStatus(SongRecordPresenter.RECORD_STATUS status) {
        if (status == SongRecordPresenter.RECORD_STATUS.STOP) {
            recordPlayIv.setImageResource(R.drawable.ic_vec_media_record_stop);
            avi.setVisibility(View.VISIBLE);
        } else {
            recordPlayIv.setImageResource(R.drawable.ic_vec_media_record);
            avi.setVisibility(View.INVISIBLE);
        }
    }

    //实时改变时间戳
    @Override
    public void changedTimeTv(String s) {
        timeTv.setText("已录制：" + s + " 秒");
    }

    //保存录音dialog
    @Override
    public void showAddDialog(final String s) {
        editDialog = new EditDialog.Builder(getCurrentContext())
                .setEditStr(s)
                .setListener(new EditDialog.Builder.OnSelectedListener() {
                    @Override
                    public void onSelectedOk(String str) {
                        presenter.saveRecordAudio(str);
                    }

                    @Override
                    public void onSelectedCancel() {
                        editDialog.dismiss();
                    }
                })
                .create();
        editDialog.show();
    }

    //是否保存成功
    @Override
    public void setAddResult(boolean isSucceed) {
        if (isSucceed) {
            ToastUtil.showToast("保存成功！");
            editDialog.dismiss();
        } else {
            ToastUtil.showToast("不能添加同名的语音");
        }
    }

    @Override
    public void onStop() {
        presenter.onStop();
        super.onStop();

    }

    @Override
    public void onDestroyView() {
        presenter.release();
        super.onDestroyView();
    }

    //一定要放在onDestroy之前
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
