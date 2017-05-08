package com.example.q.pocketmusic.module.song;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.config.CommonString;
import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.module.common.BaseActivity;
import com.example.q.pocketmusic.module.home.HomeActivity;
import com.example.q.pocketmusic.util.MyToast;
import com.example.q.pocketmusic.view.dialog.EditDialog;
import com.example.q.pocketmusic.view.widget.net.ConfettiUtil;
import com.example.q.pocketmusic.view.widget.net.HackyViewPager;
import com.example.q.pocketmusic.view.widget.net.SnackBarUtil;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import pub.devrel.easypermissions.EasyPermissions;

//查看大图界面

public class SongActivity extends BaseActivity<SongActivityPresenter.IView, SongActivityPresenter>
        implements SongActivityPresenter.IView, EasyPermissions.PermissionCallbacks {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.view_pager)
    HackyViewPager viewPager;
    @BindView(R.id.record_play_iv)
    ImageView recordPlayIv;
    @BindView(R.id.time_tv)
    TextView timeTv;
    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;
    @BindView(R.id.record_rl)
    RelativeLayout recordRl;
    @BindView(R.id.page_tv)
    TextView pageTv;
    private SongActivityAdapter adapter;


    public final static String PARAM_SONG_OBJECT_PARCEL = "PARAM_SONG_OBJECT_PARCEL";//Parcel

    public final static String LOCAL_SONG = "LOCAL_SONG";//可选的传递参数，用于传递本地的localSong

    public final static String ASK_COMMENT = "ASK_COMMENT";//可选的传递参数，用于传递某条有图的Comment

    public final static String SHARE_SONG = "SHARE_SONG";//可选传递参数，用于传递shareSong

    private EditDialog editDialog;//编辑框


    @Override
    public int setContentResource() {
        return R.layout.activity_song;
    }

    @Override
    public void initView() {
        //屏幕不灭
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //根据来自搜索和乐器类型，得到不同的song的IvUrls
        presenter.setIntent(getIntent());
        initToolbar(toolbar, presenter.getSong().getName());
        //是否隐藏录音栏
        checkHideRecord();
        //查找图片
        presenter.loadPic();
    }

    private void checkHideRecord() {
        if (presenter.getLoadingWay() == Constant.LOCAL) {
            recordRl.setVisibility(View.VISIBLE);
        } else if (presenter.getLoadingWay() == Constant.NET) {
            recordRl.setVisibility(View.GONE);
        }
    }

    //加载失败
    @Override
    public void loadFail() {
        MyToast.showToast(this, CommonString.STR_NOT_NET);
        finish();
    }


    //设置下载弹窗框
    private void setDownloadDialog() {
        //编辑框
        editDialog = new EditDialog.Builder(this)
                .setEditStr(presenter.getSong().getName())
                .setListener(new EditDialog.Builder.OnSelectedListener() {
                    @Override
                    public void onSelectedOk(String str) {
                        MyToast.showToast(getApplicationContext(), "后台下载中~");
                        presenter.download(str);
                    }

                    @Override
                    public void onSelectedCancel() {
                        editDialog.dismiss();
                    }
                })
                .create();
        editDialog.show();
    }


    //下载结果
    @Override
    public void downloadResult(Integer result, String info) {
        if (recordRl == null || toolbar == null) {
            return;
        }
        if (result.equals(Constant.FAIL)) {
            MyToast.showToast(this, info);
        } else {
            //撒花
            ConfettiUtil.getCommonConfetti(recordRl);
            //返回主页
            SnackBarUtil.IndefiniteSnackbar(toolbar, "下载成功\\(^o^)/，是否立即查看", 4000, R.color.black, SnackBarUtil.orange).setAction("是", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SongActivity.this, HomeActivity.class);
                    intent.setAction(HomeActivity.ACTION_RETURN_HOME);
                    startActivity(intent);
                }
            }).setActionTextColor(ContextCompat.getColor(this, R.color.white)).show();
        }
    }

    @Override
    public void dismissEditDialog() {
        editDialog.dismiss();
    }


    @Override
    protected SongActivityPresenter createPresenter() {
        return new SongActivityPresenter(this);
    }

    //菜单
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.download:
                setDownloadDialog();
                break;
            case R.id.collection:
                presenter.addCollection();
                break;
            case R.id.agree:
                //判断是否可以点赞
                presenter.agree();
                item.setEnabled(false);//点赞之后就不可再次点击
                break;
            case R.id.share://分享乐谱
                presenter.share();
        }
        return super.onOptionsItemSelected(item);
    }

    //不同的from加载不同的menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        presenter.CreateMenuByFrom(menu);
        return true;
    }


    //点击事件监听
    @OnClick({R.id.record_play_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.record_play_iv:
                presenter.record();
                break;
        }
    }


    @Override
    public void setPicResult(List<String> ivUrl, int from) {
        if (pageTv == null || viewPager == null) {
            return;
        }
        //本地和网络加载图片的地址有所不同
        adapter = new SongActivityAdapter(this, ivUrl, from);
        final int page = ivUrl.size();
        pageTv.setText(1 + "/" + page);//初始化
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pageTv.setText(position + 1 + "/" + page);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    //改变状态按钮图标
    @Override
    public void setBtnStatus(SongActivityPresenter.RECORD_STATUS status) {
        if (status == SongActivityPresenter.RECORD_STATUS.STOP) {
            recordPlayIv.setImageResource(R.drawable.ico_media_record_stop);
            avi.setVisibility(View.VISIBLE);
        } else {
            recordPlayIv.setImageResource(R.drawable.ico_media_record);
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
        editDialog = new EditDialog.Builder(this)
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
            MyToast.showToast(context, "保存成功！");
            editDialog.dismiss();
        } else {
            MyToast.showToast(context, "不能添加同名的语音");
        }
    }

    @Override
    public void showRefreshing(boolean isShow) {

    }

    @Override
    protected void onStop() {
        presenter.onStop();
        super.onStop();

    }

    //一定要放在onDestroy之前
    @Override
    protected void onDestroy() {
        presenter.release();
        super.onDestroy();
    }


    //权限相关
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        MyToast.showToast(context, "成功获得权限");
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        MyToast.showToast(context, "录音权限被拒绝,如需录音请到设置中心--权限管理中修改");
        //presenter.enterSystemSetting();
    }
}
