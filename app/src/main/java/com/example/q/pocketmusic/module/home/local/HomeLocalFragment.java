package com.example.q.pocketmusic.module.home.local;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.module.common.BaseFragment;
import com.example.q.pocketmusic.module.home.local.lead.LeadSongActivity;
import com.example.q.pocketmusic.view.widget.view.TopTwoTabView;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * Created by 鹏君 on 2016/8/28.
 */
public class HomeLocalFragment extends BaseFragment<HomeLocalFragmentPresenter.IView, HomeLocalFragmentPresenter>
        implements HomeLocalFragmentPresenter.IView, TopTwoTabView.TopTabListener {

    @BindView(R.id.add_local_iv)
    ImageView addLocalIv;
    @BindView(R.id.top_tab_view)
    TopTwoTabView topTwoTabView;
    @BindView(R.id.piano_iv)
    ImageView pianoIv;
    @BindView(R.id.home_local_content)
    FrameLayout homeLocalContent;
    @BindView(R.id.activity_audio_record)
    LinearLayout activityAudioRecord;
    Unbinder unbinder;

    @Override
    protected HomeLocalFragmentPresenter createPresenter() {
        return new HomeLocalFragmentPresenter(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int setContentResource() {
        return R.layout.fragment_home_local;
    }


    @Override
    public void initView() {
        //viewpager
        presenter.setFragmentManager(getChildFragmentManager());
        topTwoTabView.setListener(this);
        topTwoTabView.setCheck(0);
        presenter.clickSong();
    }


    @Override
    public void finish() {
        getActivity().finish();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LeadSongActivity.REQUEST_LEAD && resultCode == LeadSongActivity.RESULT_OK) {
            topTwoTabView.setCheck(0);
            presenter.clickSong();
        }
    }

    @Override
    public void setTopTabCheck(int position) {
        if (position == 0) {
            presenter.clickSong();
        } else {
            presenter.clickRecord();
        }
    }

    @Override
    public void onSelectRecord() {
        topTwoTabView.setCheck(1);
    }

    @Override
    public void onSelectSong() {
        topTwoTabView.setCheck(0);
    }

    @Override
    public void onSelectConvert() {

    }


    @OnClick({R.id.add_local_iv, R.id.piano_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_local_iv:
                presenter.enterLeadActivity();
                break;
            case R.id.piano_iv:
                presenter.enterPianoActivity();
                break;
        }
    }
}
