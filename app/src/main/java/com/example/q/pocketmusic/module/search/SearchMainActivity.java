package com.example.q.pocketmusic.module.search;

import android.graphics.drawable.Animatable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.module.common.BaseActivity;

import butterknife.BindView;

public class SearchMainActivity extends BaseActivity<SearchMainPresenter.IView, SearchMainPresenter>
        implements ISearchActivity, View.OnKeyListener, View.OnClickListener, SearchMainPresenter.IView, ViewPager.OnPageChangeListener {
    @BindView(R.id.search_edt)
    EditText searchEdt;
    @BindView(R.id.search_iv)
    ImageView searchIv;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    private SearchMainAdapter adapter;


    @Override
    public int setContentResource() {
        return R.layout.activity_search_main;
    }

    @Override
    public void initView() {
        searchEdt.setOnKeyListener(this);
        searchIv.setOnClickListener(this);
        viewPager.addOnPageChangeListener(this);
        adapter = new SearchMainAdapter(this, getSupportFragmentManager(), presenter.getTabsTxt(), presenter.getFragments());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabTextColors(ContextCompat.getColor(this, R.color.colorTitle), ContextCompat.getColor(this, R.color.colorTitle));
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.colorTitle));
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
        SearchMainPresenter.FLAG_NOW_FRAGMENT = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected SearchMainPresenter createPresenter() {
        return new SearchMainPresenter(this);
    }
}
