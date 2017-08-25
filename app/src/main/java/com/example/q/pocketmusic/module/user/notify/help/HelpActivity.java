package com.example.q.pocketmusic.module.user.notify.help;

import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.config.CommonString;
import com.example.q.pocketmusic.model.bean.bmob.Help;
import com.example.q.pocketmusic.module.common.BaseActivity;
import com.example.q.pocketmusic.module.user.notify.suggestion.LeftAndRightTagDecoration;
import com.example.q.pocketmusic.module.user.register.RegisterPresenter;
import com.example.q.pocketmusic.util.common.ToastUtil;
import com.jude.easyrecyclerview.EasyRecyclerView;

import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class HelpActivity extends BaseActivity<HelpPresenter.IView, HelpPresenter>
        implements SwipeRefreshLayout.OnRefreshListener, HelpPresenter.IView {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.recycler)
    EasyRecyclerView recycler;
    @BindView(R.id.activity_help)
    LinearLayout activityHelp;
    private HelpAdapter adapter;

    @Override
    public int setContentResource() {
        return R.layout.activity_help;
    }

    @Override
    public void initView() {
        adapter = new HelpAdapter(this);
        recycler.setRefreshListener(this);
        initToolbar(toolbar, "帮助信息");
        initRecyclerView(recycler, adapter);
        recycler.addItemDecoration(new LeftAndRightTagDecoration(getCurrentContext()));
        recycler.addItemDecoration(new DividerItemDecoration(getCurrentContext(), DividerItemDecoration.VERTICAL));
        onRefresh();
    }


    @Override
    public void onRefresh() {
        BmobQuery<Help> query = new BmobQuery<>();
        query.order("-createdAt");//逆序
        query.findObjects(new FindListener<Help>() {
            @Override
            public void done(List<Help> list, BmobException e) {
                if (e == null) {
                    adapter.clear();
                    adapter.addAll(list);
                } else {
                    ToastUtil.showToast(CommonString.STR_NOT_FOUND);
                }
            }
        });
    }

    @Override
    protected HelpPresenter createPresenter() {
        return new HelpPresenter(this);
    }
}
