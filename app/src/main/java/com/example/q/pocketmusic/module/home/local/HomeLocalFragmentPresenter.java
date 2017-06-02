package com.example.q.pocketmusic.module.home.local;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.module.common.BaseActivity;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.module.home.local.localrecord.LocalRecordFragment;
import com.example.q.pocketmusic.module.home.local.localsong.LocalSongFragment;
import com.example.q.pocketmusic.module.lead.LeadSongActivity;
import com.example.q.pocketmusic.module.piano.PianoActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 鹏君 on 2016/11/17.
 */

public class HomeLocalFragmentPresenter extends BasePresenter<HomeLocalFragmentPresenter.IView> {
    private IView fragment;
    private int FLAG;
    private static final int FLAG_SELECT_SONG = 1001;
    private static final int FLAG_SELECT_RECORD = 1002;
    private List<Fragment> fragments;
    private LocalRecordFragment localRecordFragment;
    private LocalSongFragment localSongFragment;
    private Fragment totalFragment;
    private FragmentManager fm;

    public void setFragmentManager(FragmentManager fm) {
        this.fm = fm;
    }

    public HomeLocalFragmentPresenter(IView fragment) {
        attachView(fragment);
        this.fragment = getIViewRef();
        initFragment();
    }

    private void initFragment() {
        fragments = new ArrayList<>();
        localRecordFragment = new LocalRecordFragment();
        localSongFragment = new LocalSongFragment();
        fragments.add(localSongFragment);
        fragments.add(localRecordFragment);
    }

    //乐曲
    public void clickSong() {
        if (FLAG != FLAG_SELECT_SONG) {
            FLAG = FLAG_SELECT_SONG;
            showFragment(fragments.get(0));
            fragment.onSelectSong();
        }
    }


    //录音
    public void clickRecord() {
        if (FLAG != FLAG_SELECT_RECORD) {
            FLAG = FLAG_SELECT_RECORD;
            showFragment(fragments.get(1));
            fragment.onSelectRecord();
        }
    }

    private void showFragment(Fragment fragment) {
        if (!fragment.isAdded()) {
            if (totalFragment == null) {
                fm.beginTransaction().add(R.id.home_local_content, fragment, fragment.getClass().getName()).commit();
            } else {
                fm.beginTransaction().hide(totalFragment).add(R.id.home_local_content, fragment, fragment.getClass().getName()).commit();
            }
        } else {
            fm.beginTransaction().hide(totalFragment).show(fragment).commit();
        }
        totalFragment = fragment;
    }

    public void enterLeadActivity() {
        Intent intent = new Intent(fragment.getCurrentContext(), LeadSongActivity.class);
        ((BaseActivity) fragment.getCurrentContext()).startActivityForResult(intent, LeadSongActivity.REQUEST_LEAD);
    }

    public void enterPianoActivity() {
        fragment.getCurrentContext().startActivity(new Intent(fragment.getCurrentContext(), PianoActivity.class));
    }


    public interface IView extends IBaseView {

        void onSelectRecord();

        void onSelectSong();
    }
}
