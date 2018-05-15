package com.example.q.pocketmusic.module.home.profile.share;

import android.content.DialogInterface;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import com.dell.fortune.tools.LogUtils;
import com.dell.fortune.tools.dialog.DialogEditSureCancel;
import com.dell.fortune.tools.dialog.DialogSureCancel;
import com.dell.fortune.tools.toast.Toasts;
import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.config.constant.InstrumentConstant;
import com.example.q.pocketmusic.data.bean.share.ShareSong;
import com.example.q.pocketmusic.module.common.AuthActivity;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

import butterknife.BindView;

public class UserShareActivity extends AuthActivity<UserSharePresenter.IView, UserSharePresenter>
        implements UserSharePresenter.IView, SwipeRefreshLayout.OnRefreshListener, RecyclerArrayAdapter.OnMoreListener, UserShareAdapter.OnSelectListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.recycler)
    EasyRecyclerView recycler;
    private UserShareAdapter adapter;
    private DialogEditSureCancel dialogEditSureCancel;

    @Override
    protected UserSharePresenter createPresenter() {
        return new UserSharePresenter(this);
    }

    @Override
    public int setContentResource() {
        return R.layout.activity_user_share;
    }

    @Override
    public void initUserView() {
        adapter = new UserShareAdapter(getCurrentContext());
        recycler.setRefreshListener(this);
        adapter.setMore(R.layout.view_more, this);
        adapter.setOnSelectListener(this);
        initToolbar(toolbar, "我的分享");
        initRecyclerView(recycler, adapter, 1);
        onRefresh();
    }


    @Override
    public void onRefresh() {
        presenter.getUserShareList(true);
    }

    @Override
    public void setList(boolean isRefreshing, List<ShareSong> list) {
        if (isRefreshing) {
            adapter.clear();
        }
        if (list == null) {
            onRefresh();
        } else {
            adapter.addAll(list);
        }
    }


    @Override
    public void onMoreShow() {
        presenter.getUserShareList(false);
    }

    @Override
    public void onMoreClick() {

    }

    @Override
    public void onSelectDelete(final int position) {
        final DialogSureCancel dialogSureCancel = new DialogSureCancel(this);
        dialogSureCancel.getTvTitle().setText("删除曲谱");
        dialogSureCancel.getTvContent().setText("是否删除  " + adapter.getItem(position).getName());
        dialogSureCancel.getTvSure().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareSong shareSong = adapter.getItem(position);
                adapter.remove(shareSong);
                presenter.deleteShareSong(shareSong);
            }
        });
        dialogSureCancel.getTvCancel().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSureCancel.cancel();
            }
        });
        dialogSureCancel.show();

    }

    @Override
    public void onSelectItem(int position) {
        presenter.enterSongActivity(adapter.getItem(position));
    }

    @Override
    public void onSelectModify(final int position) {
        dialogEditSureCancel = new DialogEditSureCancel(context);
        dialogEditSureCancel.getTvTitle().setText("修改名字");
        dialogEditSureCancel.getTvSure().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nickName = dialogEditSureCancel.getEditText().getText().toString();
                if (TextUtils.isEmpty(nickName)) {
                    Toasts.error("不能为空哦~");
                    return;
                }
                dialogEditSureCancel.dismiss();
                presenter.updateShareName(adapter.getItem(position), nickName);
            }
        });
        dialogEditSureCancel.getTvCancel().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogEditSureCancel.cancel();
            }
        });
        dialogEditSureCancel.show();
    }

    @Override
    public void onSelectChangeType(final int position) {
        new AlertDialog.Builder(getCurrentContext())
                .setTitle("选择类型")
                .setSingleChoiceItems(InstrumentConstant.typeNames, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LogUtils.e("which:" + which);
                        dialog.dismiss();
                        presenter.updateShareType(adapter.getItem(position), which);
                    }
                })
                .show();

    }
}
