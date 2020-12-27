package com.example.q.pocketmusic.module.home.net.type;


import android.content.Intent;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.data.bean.local.LocalSong;
import com.example.q.pocketmusic.data.db.LocalSongDao;
import com.example.q.pocketmusic.module.common.BaseActivity;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.module.home.net.type.community.CommunityFragment;
import com.example.q.pocketmusic.module.home.net.type.community.ask.publish.PublishAskActivity;
import com.example.q.pocketmusic.module.home.net.type.community.share.publish.ShareActivity;
import com.example.q.pocketmusic.module.home.net.type.hot.HotListFragment;
import com.example.q.pocketmusic.module.home.net.type.study.StudyActivity;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by 鹏君 on 2016/8/29.
 */
public class SongTypeActivityPresenter extends BasePresenter<SongTypeActivityPresenter.IView> {
    private static int FLAG_SELECT_HOT_LIST = 1001;
    private static int FLAG_SELECT_COMMUNITY = 1002;
    private HotListFragment hotListFragment;
    private CommunityFragment communityFragment;
    private FragmentManager fm;
    private Fragment totalFragment;
    private int FLAG;//标记当前Fragment
    private List<Fragment> fragments = new ArrayList<>();

    public SongTypeActivityPresenter(IView activity) {
        super(activity);
    }

    //进入学习版块
    public void enterStudyActivity(Integer typeId) {
        Intent intent = new Intent(mContext, StudyActivity.class);
        intent.putExtra(StudyActivity.PARAM_TYPE, typeId);
        mContext.startActivity(intent);
    }

    public void initFragment(int typeId) {
        fragments = new ArrayList<>();
        hotListFragment = HotListFragment.getInstance(typeId);
        communityFragment = CommunityFragment.getInstance(typeId);
        fragments.add(hotListFragment);
        fragments.add(communityFragment);
    }

    //热门
    public void clickHotList() {
        if (FLAG != FLAG_SELECT_HOT_LIST) {
            FLAG = FLAG_SELECT_HOT_LIST;
            showFragment(fragments.get(0));
            mView.onSelectHotList();
        }
    }

    //圈子
    public void clickCommunity() {
        if (FLAG != FLAG_SELECT_COMMUNITY) {
            FLAG = FLAG_SELECT_COMMUNITY;
            showFragment(fragments.get(1));
            mView.onSelectCommunity();
        }
    }


    private void showFragment(Fragment fragment) {
        if (!fragment.isAdded()) {
            if (totalFragment == null) {
                fm.beginTransaction().add(R.id.type_content, fragment, fragment.getClass().getName()).commit();
            } else {
                fm.beginTransaction().hide(totalFragment).add(R.id.type_content, fragment, fragment.getClass().getName()).commit();
            }
        } else {
            fm.beginTransaction().hide(totalFragment).show(fragment).commit();
        }
        totalFragment = fragment;
    }

    public void setFragmentManager(FragmentManager supportFragmentManager) {
        this.fm = supportFragmentManager;
    }

    //跳转到AskSongActivity
    public void enterPublishAskActivity(int typeId) {
        Intent intent = new Intent(mContext, PublishAskActivity.class);
        //注意这里使用的是Fragment的方法，而不能用Activity的方法
        intent.putExtra(PublishAskActivity.PARAM_TYPE_ID, typeId);
        ((BaseActivity) mView).startActivityForResult(intent, PublishAskActivity.REQUEST_ASK);
    }

    //查找本地
    public void queryLocalSongList() {
        rx.Observable.create(new rx.Observable.OnSubscribe<List<LocalSong>>() {
            @Override
            public void call(Subscriber<? super List<LocalSong>> subscriber) {
                LocalSongDao dao = new LocalSongDao(mContext);
                List<LocalSong> list = dao.queryForAll();
                subscriber.onNext(list);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<LocalSong>>() {
                    @Override
                    public void call(List<LocalSong> localSongs) {
                        List<String> strings = new ArrayList<String>();
                        for (int i = 0; i < localSongs.size(); i++) {
                            strings.add(localSongs.get(i).getName());
                        }
                        mView.alertLocalListDialog(localSongs, strings);
                    }
                });
    }

    //进入分享
    public void enterShareActivity(LocalSong localSong, int typeId) {
        Intent intent = new Intent(mContext, ShareActivity.class);
        intent.putExtra(ShareActivity.PARAM_LOCAL_SONG, localSong);
        intent.putExtra(ShareActivity.PARAM_TYPE_ID, typeId);
        mContext.startActivity(intent);
    }


    public interface IView extends IBaseView {

        void onSelectHotList();

        void onSelectCommunity();

        void alertLocalListDialog(List<LocalSong> localSongs, List<String> strings);
    }


}
