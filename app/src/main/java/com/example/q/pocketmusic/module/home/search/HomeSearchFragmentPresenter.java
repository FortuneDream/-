package com.example.q.pocketmusic.module.home.search;

import android.text.TextUtils;

import androidx.fragment.app.Fragment;

import com.dell.fortune.tools.toast.ToastUtil;
import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.module.home.search.net.SearchNetFragment;
import com.example.q.pocketmusic.module.home.search.share.SearchShareFragment;

import java.util.ArrayList;
import java.util.List;

public class HomeSearchFragmentPresenter extends BasePresenter<HomeSearchFragmentPresenter.IView> {
    private SearchNetFragment netFragment;
    private SearchShareFragment shareFragment;
    public static final int POSITION_NET_FRAGMENT = 0;//第一个位置
    public static final int POSITION_SHARE_FRAGMENT = 1;//第二个位置
    public static int FLAG_NOW_FRAGMENT = 0;
    private String inputStr;

    public void setInputStr(String inputStr) {
        this.inputStr = inputStr;
    }


    public HomeSearchFragmentPresenter(IView fragment) {
        super(fragment);
        getFragments();
        getTabsTxt();
    }

    public List<String> getTabsTxt() {
        List<String> list = new ArrayList<>();
        list.add("网络搜索");
        list.add("本站搜索");
        return list;
    }

    public List<Fragment> getFragments() {
        netFragment = new SearchNetFragment();
        shareFragment = new SearchShareFragment();
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(netFragment);
        fragments.add(shareFragment);
        return fragments;
    }

    //刷新版块
    public void searchSong(int flag) {
        if (flag == POSITION_NET_FRAGMENT) {
            netFragment.getInitSearchList();
        } else if (flag == POSITION_SHARE_FRAGMENT) {
            shareFragment.getInitSearchList();
        }
    }

    public String getQueryStr() {
        if (inputStr == null) {
            return null;
        }
        String query = inputStr.replaceAll(" ", "");//消除空格
        if (!TextUtils.isEmpty(query)) {
            return query;
        } else {
            ToastUtil.showToast(mView.getResString(R.string.complete_info));
            return null;
        }
    }

    //如果是第三页就不动，一二页都跳转到第二页
    public int setSearchItemPage() {
        return FLAG_NOW_FRAGMENT == POSITION_SHARE_FRAGMENT ? POSITION_SHARE_FRAGMENT : POSITION_NET_FRAGMENT;
    }

    interface IView extends IBaseView {

    }
}
