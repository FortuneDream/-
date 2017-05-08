package com.example.q.pocketmusic.module.search.recommend;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.model.bean.Song;
import com.example.q.pocketmusic.module.common.BaseFragment;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 81256 on 2017/4/14.
 */

//包括桃李醉春风和收藏夹
public class SearchRecommendFragment extends BaseFragment<SearchRecommendFragmentPresenter.IView, SearchRecommendFragmentPresenter>
        implements SearchRecommendFragmentPresenter.IView, TagFlowLayout.OnTagClickListener {

    @BindView(R.id.recommend_left_tv)
    TextView recommendLeftTv;
    @BindView(R.id.recommend_right_iv)
    ImageView recommendRightIv;
    @BindView(R.id.recommend_flow_layout)
    TagFlowLayout recommendFlowLayout;
    private SearchRecommendAdapter recommendAdapter;

    @Override
    public void finish() {
        getActivity().finish();
    }

    @Override
    public int setContentResource() {
        return R.layout.fragment_search_recommend;
    }

    @Override
    public void initView() {
        recommendFlowLayout.setOnTagClickListener(this);
        presenter.getRecommendList();//推荐列表
    }

    @Override
    public void setRecommendList(List<Song> list) {
        if (recommendFlowLayout == null||getContext()==null) {
            return;
        }
        recommendAdapter = new SearchRecommendAdapter(list, getContext());
        recommendFlowLayout.setAdapter(recommendAdapter);
    }


    @Override
    public boolean onTagClick(View view, int position, FlowLayout parent) {
        presenter.enterSongActivityByRecommendTag(recommendAdapter.getItem(position));
        return true;
    }

    @OnClick(R.id.recommend_right_iv)
    public void onViewClicked() {
        presenter.enterRecommendListActivity();
    }

    @Override
    protected SearchRecommendFragmentPresenter createPresenter() {
        return new SearchRecommendFragmentPresenter(this);
    }
}
