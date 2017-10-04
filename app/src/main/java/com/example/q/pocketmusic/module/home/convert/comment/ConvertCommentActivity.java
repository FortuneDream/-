package com.example.q.pocketmusic.module.home.convert.comment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.model.bean.convert.ConvertComment;
import com.example.q.pocketmusic.model.bean.convert.ConvertPost;
import com.example.q.pocketmusic.module.common.AuthActivity;
import com.example.q.pocketmusic.module.home.net.type.community.ask.comment.AskPostHeadView;
import com.example.q.pocketmusic.util.common.ToastUtil;
import com.example.q.pocketmusic.view.dialog.CoinDialogBuilder;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ConvertCommentActivity extends AuthActivity<ConvertCommentActivityPresenter.IView, ConvertCommentActivityPresenter>
        implements ConvertCommentActivityPresenter.IView, RecyclerArrayAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener
        , RecyclerArrayAdapter.OnMoreListener, AskPostHeadView.OnClickIndexListener {

    public static final String PARAM_POST = "param_post";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.recycler)
    EasyRecyclerView recycler;
    private ConvertCommentAdapter adapter;
    private ConvertPostHeadView headView;


    @Override
    public int setContentResource() {
        return R.layout.activity_convert_comment;
    }

    @Override
    public void initUserView() {
        initToolbar(toolbar, "转谱评论");
        adapter = new ConvertCommentAdapter(getCurrentContext());
        presenter.setPost((ConvertPost) getIntent().getSerializableExtra(PARAM_POST));
        headView = new ConvertPostHeadView(presenter.getPost().getCreatedAt(),
                "赞一个位置",
                presenter.getPost().getUser().getNickName(),
                presenter.getPost().getTitle(),
                presenter.getPost().getUser().getHeadImg());
        adapter.addHeader(headView);
        headView.setOnClickIndexListener(this);
        initRecyclerView(recycler, adapter);
        adapter.setOnItemClickListener(this);
        recycler.setRefreshListener(this);
        adapter.setMore(R.layout.view_more, this);
        presenter.getCommentList(true);
    }


    @Override
    protected ConvertCommentActivityPresenter createPresenter() {
        return new ConvertCommentActivityPresenter(this);
    }


    @Override
    public void onItemClick(final int position) {
        //查看部分
        final String content = adapter.getItem(position).getContent();
        String partContent = content.substring(0, content.length() / 3);
        new AlertDialog.Builder(getCurrentContext())
                .setMessage(partContent)
                .setPositiveButton("查看完整", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertCoinDialog(content, adapter.getItem(position).getPost().getCoin());
                    }
                })
                .show();

    }


    //应该是先给钱
    private void alertCoinDialog(String content, int coin) {
        new AlertDialog.Builder(getCurrentContext())
                .setMessage(content)
                .show();
    }

    @Override
    public void onRefresh() {
        presenter.getCommentList(true);
    }

    @Override
    public void onMoreShow() {
        presenter.getMoreCommentList();
    }

    @Override
    public void onMoreClick() {

    }

    @Override
    public void setCommentList(boolean isRefreshing, List<ConvertComment> list) {
        if (isRefreshing) {
            adapter.clear();
        }
        adapter.addAll(list);
    }


    @Override
    public void onClick() {
        //查看图片
        presenter.getConvertObject();
    }
}