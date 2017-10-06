package com.example.q.pocketmusic.module.home.local.localconvert;

import com.example.q.pocketmusic.model.bean.local.LocalConvertSong;
import com.example.q.pocketmusic.model.db.LocalConvertSongDao;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class LocalConvertFragmentPresenter extends BasePresenter<LocalConvertFragmentPresenter.IView> {
    private IView fragment;
    private LocalConvertSongDao localConvertSongDao;

    public LocalConvertFragmentPresenter(IView fragment) {
        attachView(fragment);
        this.fragment = getIViewRef();
        localConvertSongDao = new LocalConvertSongDao(fragment.getAppContext());
    }

    public void getConvertContent(LocalConvertSong item) {
        LocalConvertSong localConvertSong = localConvertSongDao.findByName(item.getName());
        fragment.alertContentDialog(localConvertSong);
    }

    //异步取数据
    public void getList() {
        Observable.create(new Observable.OnSubscribe<List<LocalConvertSong>>() {
            @Override
            public void call(Subscriber<? super List<LocalConvertSong>> subscriber) {
                subscriber.onNext(getLocalConvertList());
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<LocalConvertSong>>() {
                    @Override
                    public void call(List<LocalConvertSong> localConvertSongs) {
                        fragment.setList(localConvertSongs);
                    }
                });
    }

    private List<LocalConvertSong> getLocalConvertList() {
        return localConvertSongDao.queryForAll();
    }

    public void deleteLocalConvertSong(LocalConvertSong localConvertSong) {
        localConvertSongDao.delete(localConvertSong);
    }

    interface IView extends IBaseView {

        void alertContentDialog(LocalConvertSong localConvertSong);

        void setList(List<LocalConvertSong> localConvertSongs);
    }
}
