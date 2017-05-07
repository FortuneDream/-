package com.example.q.pocketmusic.module.search;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.config.CommonString;
import com.example.q.pocketmusic.module.search.net.SearchNetFragment;
import com.example.q.pocketmusic.module.search.recommend.SearchRecommendFragment;
import com.example.q.pocketmusic.module.search.share.SearchShareFragment;
import com.example.q.pocketmusic.util.MyToast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 81256 on 2017/4/14.
 */

public class SearchMainPresenter {
    private Context context;
    private IView activity;
    private SearchRecommendFragment recommendFragment;
    private SearchNetFragment netFragment;
    private SearchShareFragment shareFragment;
    public static final int POSITION_RECOMMEND_FRAGMENT = 0;//第一个位置
    public static final int POSITION_NET_FRAGMENT = 1;//第二个位置
    public static final int POSITION_SHARE_FRAGMENT = 2;//第三个位置
    public static int FLAG_NOW_FRAGMENT = 0;
    private String inputStr;

    public void setInputStr(String inputStr) {
        this.inputStr = inputStr;
    }

    public SearchMainPresenter(Context context, IView activity) {
        this.context = context;
        this.activity = activity;
        getFragments();
        getTabsTxt();
    }

    public List<String> getTabsTxt() {
        List<String> list = new ArrayList<>();
        list.add("热点曲谱");
        list.add("网络搜索");
        list.add("本站搜索");
        return list;
    }

    public List<Fragment> getFragments() {
        recommendFragment = new SearchRecommendFragment();
        netFragment = new SearchNetFragment();
        shareFragment = new SearchShareFragment();
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(recommendFragment);
        fragments.add(netFragment);
        fragments.add(shareFragment);
        return fragments;
    }

    //刷新版块
    public void onRefreshFragment(int flag) {
        if (flag == POSITION_NET_FRAGMENT) {
            netFragment.onRefresh();
        } else if (flag == POSITION_SHARE_FRAGMENT) {
            shareFragment.onRefresh();
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
            MyToast.showToast(context, CommonString.STR_COMPLETE_INFO);
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
