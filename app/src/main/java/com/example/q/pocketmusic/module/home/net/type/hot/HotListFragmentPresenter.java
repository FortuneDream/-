package com.example.q.pocketmusic.module.home.net.type.hot;

import com.example.q.pocketmusic.config.constant.Constant;
import com.example.q.pocketmusic.data.bean.Song;
import com.example.q.pocketmusic.data.bean.SongObject;
import com.example.q.pocketmusic.data.net.LoadTypeSongList;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.module.song.SongActivity;
import com.example.q.pocketmusic.config.constant.InstrumentConstant;

import java.util.List;


public class HotListFragmentPresenter extends BasePresenter<HotListFragmentPresenter.IView> {
    private IView fragment;
    private int mPage;
    private int typeId;

    public HotListFragmentPresenter(IView activity) {
        attachView(activity);
        this.fragment = getIViewRef();
    }

    public int getmPage() {
        return mPage;
    }


    public void getList(int typeId, final boolean isRefreshing) {
        String url = Constant.BASE_URL + "/qiyue/" + InstrumentConstant.getUrl(typeId) + mPage + ".html";
        new LoadTypeSongList(typeId) {
            @Override
            protected void onPostExecute(List<Song> songs) {
                super.onPostExecute(songs);
                if (!isRefreshing) {
                    fragment.setList(songs);
                } else {
                    fragment.setListWithRefreshing(songs);
                }
            }
        }.execute(url);
    }


    public void setPage(int page) {
        this.mPage = page;
    }

    public void enterSongActivity(Song song) {
        SongObject object = new SongObject(song, Constant.FROM_TYPE, Constant.MENU_DOWNLOAD_COLLECTION_SHARE, Constant.NET);
        fragment.getCurrentContext().startActivity(
                SongActivity.buildTypeIntent(fragment.getCurrentContext(), object, typeId)
        );
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }


    public interface IView extends IBaseView {

        void setList(List<Song> songs);

        void setListWithRefreshing(List<Song> songs);
    }
}
