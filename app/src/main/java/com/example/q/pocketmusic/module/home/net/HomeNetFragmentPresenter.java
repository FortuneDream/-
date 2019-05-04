package com.example.q.pocketmusic.module.home.net;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;

import androidx.appcompat.app.AlertDialog;

import com.example.q.pocketmusic.config.constant.Constant;
import com.example.q.pocketmusic.data.bean.Song;
import com.example.q.pocketmusic.data.bean.SongObject;
import com.example.q.pocketmusic.data.net.LoadRecommendList;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.module.home.net.help.HelpActivity;

import com.example.q.pocketmusic.module.home.net.type.SongTypeActivity;
import com.example.q.pocketmusic.module.home.profile.suggestion.SuggestionActivity;
import com.example.q.pocketmusic.module.song.SongActivity;

import java.util.List;

/**
 * Created by 鹏君 on 2016/8/29.
 */
public class HomeNetFragmentPresenter extends BasePresenter<HomeNetFragmentPresenter.IView> {
    private int mPage;

    public HomeNetFragmentPresenter(IView fragment) {
        super(fragment);
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
                mView.setList(isRefreshing, songs);

            }
        }.execute(url);
    }

    public void enterSongActivity(Song song) {
        SongObject object = new SongObject(song, Constant.FROM_RECOMMEND, Constant.MENU_DOWNLOAD_COLLECTION_SHARE, Constant.NET);
        mView.getCurrentContext().startActivity(SongActivity.buildRecommendIntent(mView.getCurrentContext(), object));
    }


    //进入乐器类型界面
    public void enterTypeActivity(int position) {
        Intent intent = new Intent(mView.getCurrentContext(), SongTypeActivity.class);
        intent.putExtra(SongTypeActivity.PARAM_POSITION, position);
        mView.getCurrentContext().startActivity(intent);
    }

    //Banner
    public void enterBannerActivity(int picPosition) {
        if (picPosition == 0) {
            new AlertDialog.Builder(mView.getCurrentContext())
                    .setTitle("加入粉丝群")
                    .setMessage("是否跳转到加群界面？")
                    .setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            joinQQGroup();//加群
                        }
                    })
                    .show();
        } else if (picPosition == 1) {
            mView.getCurrentContext().startActivity(new Intent(mView.getCurrentContext(), HelpActivity.class));
        }
    }

    //加群
    public boolean joinQQGroup() {
        String key = "CaPRvnhGGUsxrAr1vpZ1_2x-a-eib0N5";
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + key));
        // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            mView.getCurrentContext().startActivity(intent);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void enterHelpActivity() {
        mView.getCurrentContext().startActivity(new Intent(mView.getCurrentContext(), HelpActivity.class));
    }

    public void enterSuggestionActivity() {
        mView.getCurrentContext().startActivity(new Intent(mView.getCurrentContext(), SuggestionActivity.class));
    }

    public interface IView extends IBaseView {
        void setList(boolean isRefreshing, List<Song> list);
    }

}
