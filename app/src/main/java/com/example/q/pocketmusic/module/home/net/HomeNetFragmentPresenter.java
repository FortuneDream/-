package com.example.q.pocketmusic.module.home.net;

import android.content.Intent;
import android.net.Uri;

import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.Song;
import com.example.q.pocketmusic.model.bean.SongObject;
import com.example.q.pocketmusic.model.net.LoadRecommendList;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.module.home.net.help.HelpActivity;
import com.example.q.pocketmusic.module.home.net.special.SpecialColumnActivity;
import com.example.q.pocketmusic.module.home.net.type.SongTypeActivity;
import com.example.q.pocketmusic.module.home.profile.suggestion.SuggestionActivity;
import com.example.q.pocketmusic.module.song.SongActivity;

import java.util.List;

/**
 * Created by 鹏君 on 2016/8/29.
 */
public class HomeNetFragmentPresenter extends BasePresenter<HomeNetFragmentPresenter.IView> {
    private IView fragment;
    private int mPage;

    public HomeNetFragmentPresenter(IView fragment) {
        attachView(fragment);
        this.fragment = getIViewRef();
        mPage = 0;
    }

    //可以得到推荐列表
    public void getList(final boolean isRefreshing) {
        mPage++;
        if (isRefreshing) {
            mPage = 1;
        }
        String url = Constant.RECOMMEND_LIST_URL + mPage + ".html";
        new LoadRecommendList() {
            @Override
            protected void onPostExecute(List<Song> songs) {
                super.onPostExecute(songs);
                fragment.setList(isRefreshing, songs);

            }
        }.execute(url);
    }

    public void enterSongActivity(Song song) {
        Intent intent = new Intent(fragment.getCurrentContext(), SongActivity.class);
        SongObject object = new SongObject(song, Constant.FROM_RECOMMEND, Constant.MENU_DOWNLOAD_COLLECTION_SHARE, Constant.NET);
        intent.putExtra(SongActivity.PARAM_SONG_OBJECT_SERIALIZABLE, object);
        fragment.getCurrentContext().startActivity(intent);
    }


    //进入乐器类型界面
    public void enterTypeActivity(int position) {
        Intent intent = new Intent(fragment.getCurrentContext(), SongTypeActivity.class);
        intent.putExtra(SongTypeActivity.PARAM_POSITION, position);
        fragment.getCurrentContext().startActivity(intent);
    }

    //Banner
    public void enterBannerActivity(int picPosition) {
        if (picPosition == 0) {
            joinQQGroup();
        } else if (picPosition == 1) {
            fragment.getCurrentContext().startActivity(new Intent(fragment.getCurrentContext(), SpecialColumnActivity.class));
        } else if (picPosition == 2) {
            fragment.getCurrentContext().startActivity(new Intent(fragment.getCurrentContext(), HelpActivity.class));
        }
    }

    //加群
    public boolean joinQQGroup() {
        String key = "CaPRvnhGGUsxrAr1vpZ1_2x-a-eib0N5";
        Intent intent = new Intent();
        intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + key));
        // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            fragment.getCurrentContext().startActivity(intent);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void enterHelpActivity() {
        fragment.getCurrentContext().startActivity(new Intent(fragment.getCurrentContext(), HelpActivity.class));
    }

    public void enterSuggestionActivity() {
        fragment.getCurrentContext().startActivity(new Intent(fragment.getCurrentContext(), SuggestionActivity.class));
    }

    public interface IView extends IBaseView {
        void setList(boolean isRefreshing, List<Song> list);
    }

}
