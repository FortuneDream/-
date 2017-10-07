package com.example.q.pocketmusic.module.home.convert.comment;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.callback.AbsOnClickItemHeadListener;
import com.example.q.pocketmusic.model.bean.convert.ConvertComment;
import com.example.q.pocketmusic.model.bean.convert.ConvertPost;
import com.example.q.pocketmusic.model.bean.convert.ConvertSong;
import com.example.q.pocketmusic.module.common.AuthActivity;
import com.example.q.pocketmusic.module.home.convert.comment.convert.ConvertActivity;
import com.example.q.pocketmusic.module.home.net.type.community.ask.comment.AskPostHeadView;
import com.example.q.pocketmusic.util.common.ToastUtil;
import com.example.q.pocketmusic.view.dialog.CoinDialogBuilder;
import com.example.q.pocketmusic.view.widget.view.MorePopupWindow;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ConvertCommentActivity extends AuthActivity<ConvertCommentActivityPresenter.IView, ConvertCommentActivityPresenter>
        implements ConvertCommentActivityPresenter.IView, SwipeRefreshLayout.OnRefreshListener, RecyclerArrayAdapter.OnMoreListener {
    public static final String PARAM_POST = "param_post";//帖子
    public static final int REQUEST_TITLE = 1001;//得到标题
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.recycler)
    EasyRecyclerView recycler;
    @BindView(R.id.state_tv)
    TextView stateTv;
    @BindView(R.id.user_input_edt)
    EditText userInputEdt;
    @BindView(R.id.send_comment_btn)
    Button sendCommentBtn;
    @BindView(R.id.input_ll)
    LinearLayout inputLl;
    @BindView(R.id.select_convert_from_fab)
    FloatingActionButton selectConvertFromFab;
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
        initRecyclerView(recycler, adapter, 1);
        presenter.setPost((ConvertPost) getIntent().getSerializableExtra(PARAM_POST));
        headView = new ConvertPostHeadView(presenter.getPost().getCreatedAt(),
                "赞一个位置",
                presenter.getPost().getUser().getNickName(),
                presenter.getPost().getTitle(),
                presenter.getPost().getUser().getHeadImg(),
                presenter.getPost().getCoin());
        adapter.addHeader(headView);
        adapter.setAbsOnClickItemHeadListener(new AbsOnClickItemHeadListener() {
            @Override
            public void onClickItem(int position) {
                checkPartConvertSong(position);
            }
        });
        recycler.setRefreshListener(this);
        adapter.setMore(R.layout.view_more, this);
        presenter.getCommentList(true);
    }


    @OnClick({R.id.select_convert_from_fab, R.id.send_comment_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.select_convert_from_fab:
                MorePopupWindow popupWindow = new MorePopupWindow(getCurrentContext());
                popupWindow.addView(popupWindow.getContentLl(R.drawable.ic_vec_profile_convert, "暂存中选择"));
                popupWindow.addView(popupWindow.getContentLl(R.drawable.ic_vec_check_convert_song, "在线编辑"));
                popupWindow.setListener(new MorePopupWindow.OnSelectedListener() {
                    @Override
                    public void onSelected(int position) {
                        switch (position) {
                            case 0://暂存中选择
//                                presenter.getTemporaryConvertList();
                                ToastUtil.showToast("暂不支持");
                                break;
                            case 1:
                                presenter.getConvertObject();//进入编辑页面
                                break;
                        }
                    }
                });
                popupWindow.show(view);
                break;
            case R.id.send_comment_btn:
                String title = userInputEdt.getText().toString();
                presenter.sendConvertComment(title, presenter.getContent());
                break;
        }
    }


    //查看部分转谱
    private void checkPartConvertSong(final int position) {
        final String content = adapter.getItem(position).getContent();
        String partContent = content.substring(0, content.length() / 3);
        new AlertDialog.Builder(getCurrentContext())
                .setMessage(partContent + "...")
                .setPositiveButton("查看完整", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.checkUserHasConsumeCoin(adapter.getItem(position));//检查是否已经购买
                    }
                })
                .show();
    }


    @Override
    protected ConvertCommentActivityPresenter createPresenter() {
        return new ConvertCommentActivityPresenter(this);
    }


    @Override
    public void onRefresh() {
        presenter.getCommentList(true);
    }

    @Override
    public void showListDialog(List<ConvertSong> list) {

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


    //弹出完整转谱
    @Override
    public void alertCompleteConvertSongDialog(ConvertComment item) {
        new AlertDialog.Builder(getCurrentContext())
                .setTitle(item.getTitle())
                .setMessage(item.getContent())
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TITLE && resultCode == RESULT_OK) {
            //检测文字并发送t
            String title = data.getStringExtra(ConvertActivity.RESULT_PARAM_TITLE);
            String content = data.getStringExtra(ConvertActivity.RESULT_PARAM_CONTENT);
            presenter.setContent(content);
            stateTv.setText("已添加");
            ToastUtil.showToast("已添加");
            userInputEdt.setText(title);
        }
    }

    //弹出消费
    @Override
    public void alertCoinDialog(final ConvertComment item, final int coin) {
        new CoinDialogBuilder(getCurrentContext(), coin)
                .setPositiveButton(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.performConsume(item, coin);//消费硬币
                    }
                })
                .show();
    }
}