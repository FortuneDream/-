package com.example.q.pocketmusic.module.user.notify.suggestion;

import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
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
import butterknife.OnClick;

/**
 * Created by 鹏君 on 2016/11/14.
 */

public class SuggestionActivity extends AuthActivity<SuggestionPresenter.IView, SuggestionPresenter>
        implements SuggestionPresenter.IView, SwipeRefreshLayout.OnRefreshListener {
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

    private SuggestionAdapter adapter;

    @Override
    public int setContentResource() {
        return R.layout.activity_suggestion;
    }


    @Override
    public void initUserView() {
        recycler.setRefreshListener(this);
        presenter.setUser(user);
        adapter = new SuggestionAdapter(this);
        initToolbar(toolbar, "反馈意见");
        initRecyclerView(recycler, adapter);
        recycler.addItemDecoration(new LeftAndRightTagDecoration(getCurrentContext()));
        recycler.addItemDecoration(new DividerItemDecoration(getCurrentContext(), DividerItemDecoration.VERTICAL));
        presenter.getSuggestionList(false);
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
    public void getSuggestionListResultWithRefreshing(List<UserSuggestion> list) {
        adapter.clear();
        adapter.addAll(list);
    }

    @Override
    public void onRefresh() {
        presenter.getSuggestionList(true);
    }


    @Override
    protected SuggestionPresenter createPresenter() {
        return new SuggestionPresenter(this);
    }
}