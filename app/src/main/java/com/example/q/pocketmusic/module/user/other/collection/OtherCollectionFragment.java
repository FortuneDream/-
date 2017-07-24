package com.example.q.pocketmusic.module.user.other.collection;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.model.bean.MyUser;
import com.example.q.pocketmusic.model.bean.collection.CollectionSong;
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

public class OtherCollectionFragment extends AuthFragment<OtherCollectionPresenter.IView, OtherCollectionPresenter>
        implements OtherCollectionPresenter.IView {
    @BindView(R.id.collection_recycler)
    EasyRecyclerView collectionRecycler;
    private OtherCollectionAdapter adapter;
    private MyUser other;

    @Override
    public int setContentResource() {
        return R.layout.fragment_other_collection;
    }

    @Override
    public void initView() {
        adapter = new OtherCollectionAdapter(getCurrentContext());
        initRecyclerView(collectionRecycler, adapter, 1);
        other = ((OtherProfileActivity) getActivity()).otherUser;
        presenter.getOtherCollectionList(other);
    }

    @Override
    protected OtherCollectionPresenter createPresenter() {
        return new OtherCollectionPresenter(this);
    }


    @Override
    public void setOtherCollectionList(List<CollectionSong> list) {
        adapter.addAll(list);
    }
}
