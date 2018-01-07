package com.example.q.pocketmusic.module.home.search;


import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.module.common.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HomeSearchFragment extends BaseFragment<HomeSearchFragmentPresenter.IView, HomeSearchFragmentPresenter>
        implements HomeSearchFragmentPresenter.IView, View.OnKeyListener, View.OnClickListener, ViewPager.OnPageChangeListener,
        ISearchActivity {
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.search_edt)
    EditText searchEdt;
    @BindView(R.id.search_iv)
    ImageView searchIv;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    private HomeSearchAdapter adapter;

    @Override
    public int setContentResource() {
        return R.layout.fragment_home_search;
    }

    @Override
    public void initView() {
        searchEdt.setOnKeyListener(this);
        searchIv.setOnClickListener(this);
        viewPager.addOnPageChangeListener(this);
        adapter = new HomeSearchAdapter(getChildFragmentManager(), presenter.getTabsTxt(), presenter.getFragments());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabTextColors(ContextCompat.getColor(getCurrentContext(), R.color.colorTitle), ContextCompat.getColor(getCurrentContext(), R.color.colorTitle));
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getCurrentContext(), R.color.colorTitle));
    }

    //Enter键
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
            beginSearch();
            return true;
        }
        return false;
    }

    //传给Fragment
    @Override
    public String getQueryStr() {
        return presenter.getQueryStr();
    }


    //搜索键,得到输入
    @Override
    public void onClick(View v) {
        beginSearch();
    }

    //得到输入，跳转到第二页，刷新
    public void beginSearch() {
        if (searchIv.getDrawable() instanceof Animatable) {
            ((Animatable) (searchIv.getDrawable())).start();
        }
        presenter.setInputStr(searchEdt.getText().toString().trim());
        viewPager.setCurrentItem(presenter.setSearchItemPage(), true);
        presenter.searchSong(viewPager.getCurrentItem());
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        HomeSearchFragmentPresenter.FLAG_NOW_FRAGMENT = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
    @Override
    protected HomeSearchFragmentPresenter createPresenter() {
        return new HomeSearchFragmentPresenter(this);
    }

}