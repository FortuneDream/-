package com.example.q.pocketmusic.module.user.suggestion;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.model.bean.bmob.UserSuggestion;
import com.example.q.pocketmusic.module.common.AuthActivity;
import com.jude.easyrecyclerview.EasyRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Cloud on 2016/11/14.
 */

public class SuggestionActivity extends AuthActivity implements SuggestionPresenter.IView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.recycler)
    EasyRecyclerView recycler;
    @BindView(R.id.user_input_edt)
    EditText userInputEdt;
    @BindView(R.id.send_suggestion_btn)
    Button sendSuggestionBtn;
    @BindView(R.id.input_ll)
    LinearLayout inputLl;
    private SuggestionPresenter presenter;
    private SuggestionAdapter adapter;

    @Override
    public int setContentResource() {
        return R.layout.activity_suggestion;
    }

    @Override
    public void setListener() {
        recycler.setRefreshListener(this);
    }

    @Override
    public void initView() {
        presenter = new SuggestionPresenter(this, this, user);
        adapter = new SuggestionAdapter(this);
        initToolbar(toolbar, "反馈意见");
        initRecyclerView(recycler, adapter);
        adapter.addHeader(new SuggestionHeader(context));
        onRefresh();
    }


    @OnClick(R.id.send_suggestion_btn)
    public void onClick() {
        String suggestion = userInputEdt.getText().toString().trim();
        userInputEdt.setText("");
        presenter.sendSuggestion(suggestion);
    }

    @Override
    public void sendSuggestionResult(UserSuggestion userSuggestion) {
        adapter.add(userSuggestion);
    }

    @Override
    public void getSuggestionListResult(List<UserSuggestion> list) {
        adapter.addAll(list);
    }

    @Override
    public void onRefresh() {
        adapter.clear();
        presenter.getSuggestionList();
    }

    @Override
    public void showRefreshing(boolean isShow) {
        recycler.setRefreshing(isShow);
    }

}