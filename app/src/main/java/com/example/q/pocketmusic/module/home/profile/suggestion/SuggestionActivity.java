package com.example.q.pocketmusic.module.home.profile.suggestion;

import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.data.bean.bmob.UserSuggestion;
import com.example.q.pocketmusic.module.common.AuthActivity;
import com.google.android.material.appbar.AppBarLayout;
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
        adapter = new SuggestionAdapter(this);
        initToolbar(toolbar, "反馈意见");
        initRecyclerView(recycler, adapter);
        recycler.addItemDecoration(new LeftAndRightTagDecoration(getCurrentContext()));
        recycler.addItemDecoration(new DividerItemDecoration(getCurrentContext(), DividerItemDecoration.VERTICAL));
        presenter.getSuggestionList(true);
    }


    @OnClick(R.id.send_suggestion_btn)
    public void onClick() {
        String suggestion = userInputEdt.getText().toString().trim();
        userInputEdt.setText("");
        presenter.sendSuggestion(suggestion);
    }


    @Override
    public void onRefresh() {
        presenter.getSuggestionList(true);
    }


    @Override
    protected SuggestionPresenter createPresenter() {
        return new SuggestionPresenter(this);
    }

    @Override
    public void setSuggestionList(boolean isRefreshing, List<UserSuggestion> list) {
        if (isRefreshing) {
            adapter.clear();
        }
        adapter.addAll(list);
    }
}