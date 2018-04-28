package com.example.q.pocketmusic.module.home.local;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.module.common.BaseActivity;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.module.home.local.lead.LeadSongActivity;
import com.example.q.pocketmusic.module.home.local.localrecord.LocalRecordFragment;
import com.example.q.pocketmusic.module.home.local.localsong.LocalSongFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 鹏君 on 2016/11/17.
 */

public class HomeLocalFragmentPresenter extends BasePresenter<HomeLocalFragmentPresenter.IView> {
    public interface TabType {
        int SONG = 0;
        int RECORD = 1;
        int DEFAULT_INDEX = -1;
    }

    private int mCurIndex = TabType.DEFAULT_INDEX;
    private List<Fragment> fragments;
    private LocalRecordFragment localRecordFragment;
    private LocalSongFragment localSongFragment;
    private Fragment totalFragment;
    private FragmentManager fm;

    public void setFragmentManager(FragmentManager fm) {
        this.fm = fm;
    }

    public HomeLocalFragmentPresenter(IView fragment) {
       super(fragment);
        initFragment();
    }

    private void initFragment() {
        fragments = new ArrayList<>();
        localRecordFragment = new LocalRecordFragment();
        localSongFragment = new LocalSongFragment();
        fragments.add(TabType.SONG, localSongFragment);
        fragments.add(TabType.RECORD, localRecordFragment);
    }

    public void clickBottomTab(int index) {
        if (mCurIndex != index) {
            showFragment(fragments.get(index));
            mView.onSelectTabResult(mCurIndex,index);
            mCurIndex = index;
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
        Intent intent = new Intent(mView.getCurrentContext(), LeadSongActivity.class);
        ((BaseActivity) mView.getCurrentContext()).startActivityForResult(intent, LeadSongActivity.REQUEST_LEAD);
    }


    public interface IView extends IBaseView {

        void onSelectTabResult(int oldIndex, int index);
    }
}
