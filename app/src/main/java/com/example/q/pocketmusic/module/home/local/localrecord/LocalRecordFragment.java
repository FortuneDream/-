package com.example.q.pocketmusic.module.home.local.localrecord;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.model.bean.local.RecordAudio;
import com.example.q.pocketmusic.module.common.BaseFragment;
import com.example.q.pocketmusic.util.LogUtils;
import com.example.q.pocketmusic.util.MyToast;
import com.github.rubensousa.bottomsheetbuilder.BottomSheetBuilder;
import com.github.rubensousa.bottomsheetbuilder.BottomSheetMenuDialog;
import com.github.rubensousa.bottomsheetbuilder.adapter.BottomSheetItemClickListener;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cloud on 2016/11/17.
 */

public class LocalRecordFragment extends BaseFragment<LocalRecordFragmentPresenter.IView,LocalRecordFragmentPresenter>
        implements LocalRecordFragmentPresenter.IView, SwipeRefreshLayout.OnRefreshListener, RecyclerArrayAdapter.OnItemClickListener,
        LocalRecordFragmentAdapter.OnSelectListener {
    @BindView(R.id.recycler)
    EasyRecyclerView recycler;
    @BindView(R.id.activity_audio_record)
    LinearLayout activityAudioRecord;
    private SeekBar seekBar;
    private ImageView playIv;
    private TextView durationTv;
    private TextView titleTv;
    private Button closeBtn;
    private LocalRecordFragmentAdapter adapter;
    private AlertDialog dialog;


    @Override
    public void finish() {
        getActivity().finish();
    }

    @Override
    public int setContentResource() {
        return R.layout.fragment_local_record;
    }

    @Override
    public void initView() {
        //监听
        adapter = new LocalRecordFragmentAdapter(getActivity());
        adapter.setOnItemClickListener(this);
        adapter.setListener(this);
        recycler.setRefreshListener(this);

        //初始化
        initRecyclerView(recycler, adapter, 1, true);
        //加载录音列表
        recycler.setRefreshing(true);
        presenter.loadRecordList();
    }


    @Override
    public void onItemClick(int position) {
        alertPlayerDialog(position);
    }

    @Override
    public void onSelectMore(int position) {
        alertDeleteDialog(adapter.getItem(position));
    }


    //加载录音列表
    @Override
    public void setList(List<RecordAudio> list) {
        LogUtils.e(TAG, "录音数目：" + list.size());
        adapter.clear();
        adapter.addAll(list);
        recycler.setRefreshing(false);
    }

    //播放dialog
    @Override
    public void setPlayOrPauseImage(boolean status) {
        if (status) {
            playIv.setImageResource(R.drawable.ico_media_play);
        } else {
            playIv.setImageResource(R.drawable.ico_media_pause);
        }
    }

    //删除dialog
    private void alertDeleteDialog(final RecordAudio recordAudio) {
        BottomSheetMenuDialog dialog = new BottomSheetBuilder(getContext())
                .setMode(BottomSheetBuilder.MODE_LIST)
                .setMenu(R.menu.menu_home_local)
                .setItemClickListener(new BottomSheetItemClickListener() {
                    @Override
                    public void onBottomSheetItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.upload://上传分享
                                MyToast.showToast(context, "目前暂不支持分享");
                                break;
                            case R.id.delete://删除
                                presenter.deleteRecord(recordAudio);
                                adapter.remove(recordAudio);
                                if (adapter.getCount() == 0) {
                                    recycler.showEmpty();
                                }
                                break;
                        }
                    }
                })
                .createDialog();
        dialog.show();
    }

    //刷新
    @Override
    public void onRefresh() {
        presenter.synchronizedRecord();
    }


    /**
     *
     *
     * 以下内容均为录音播放
     *
     */

    /**
     * --------------流程图-----------------
     * registerReceiver(注册接受者)
     * startService(开启服务,获取列表)
     * bindService(绑定服务，返回binder到conn中得到mService代理对象)
     * -----------------------------------------
     * mService.openAudio(MediaPlayer的初始化,设置一些监听，如prepareAsync监听)
     * prepare之后开始play(),并发送广播给接受者，然后进行界面的初始化
     * 接受者使用Handler发消息给MessageQueue，使用handleMessage处理，并在这里递归延迟一秒发送消息，且根据mService中的MediaPlayer的播放来更改进度条
     * -----------------------------------------------------------------
     * 关闭时,isDestroy保证了进度条不在更新，
     * 停止mService中的MediaPlayer，解绑Service，注销接受者
     * ------------------------------
     * 播放完成,onCompleteListener，发送消息，播放器,seekBar,图标重新初始化
     *
     * @param position
     */
    private void alertPlayerDialog(int position) {
        //初始化
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_audio_play, null);
        dialog = new AlertDialog.Builder(getContext()).setView(view).setCancelable(false).create();
        playIv = (ImageView) view.findViewById(R.id.play_iv);
        seekBar = (SeekBar) view.findViewById(R.id.seek_bar);
        titleTv = (TextView) view.findViewById(R.id.title_tv);
        durationTv = (TextView) view.findViewById(R.id.duration_tv);
        closeBtn = (Button) view.findViewById(R.id.close_btn);

        //注册广播接受者,并开启，绑定服务
        presenter.registerReceiver();
        presenter.bindService(position);

        //按钮初始化
        setPlayOrPauseImage(true);
        //手动拖动
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    presenter.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //点击播放,暂停
        playIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean status = presenter.playOrPause();
                //true表示播放，false表示暂停
                setPlayOrPauseImage(status);
            }
        });

        //关闭
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.closeMedia();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    //初始化
    @Override
    public void setViewStatus(String name, String time, int duration) {
        titleTv.setText(name);
        durationTv.setText(time);
        seekBar.setMax(duration);
    }

    //每秒更新seekBar
    @Override
    public void updateProgress(int currentPosition, String time) {
        seekBar.setProgress(currentPosition);
        durationTv.setText(time);
    }

    @Override
    protected LocalRecordFragmentPresenter createPresenter() {
        return new LocalRecordFragmentPresenter();
    }


//    //退出前台,关闭音乐
//    @Override
//    public void onStop() {
//        super.onStop();
//        presenter.closeMedia();
//        dialog.dismiss();
//    }
}
