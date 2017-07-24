package com.example.q.pocketmusic.module.user.other.share;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.model.bean.MyUser;
import com.example.q.pocketmusic.model.bean.share.ShareSong;
import com.example.q.pocketmusic.module.common.AuthFragment;
import com.example.q.pocketmusic.module.user.other.OtherProfileActivity;
import com.jude.easyrecyclerview.EasyRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 鹏君 on 2017/7/24.
 * （￣m￣）
 */

public class OtherShareFragment extends AuthFragment<OtherSharePresenter.IView, OtherSharePresenter>
        implements OtherSharePresenter.IView {
    @BindView(R.id.share_recycler)
    EasyRecyclerView shareRecycler;
    private OtherShareAdapter adapter;
    private MyUser other;

    @Override
    public int setContentResource() {
        return R.layout.fragment_other_share;
    }

    @Override
    public void initView() {
        adapter = new OtherShareAdapter(getCurrentContext());
        initRecyclerView(shareRecycler, adapter, 1);
        other = ((OtherProfileActivity) getActivity()).otherUser;
        presenter.getOtherShareList(other);
    }

    @Override
    protected OtherSharePresenter createPresenter() {
        return new OtherSharePresenter(this);
    }

    @Override
    public void setOtherShareList(List<ShareSong> list) {
        adapter.addAll(list);
    }
}
