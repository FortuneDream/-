package com.example.q.pocketmusic.module.home.seek.share;

import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.model.bean.share.ShareSong;
import com.example.q.pocketmusic.util.BmobUtil;

/**
 * Created by 鹏君 on 2017/5/16.
 */

public class ShareListModel {
    private BmobUtil bmobUtil;

    public ShareListModel() {
        bmobUtil = new BmobUtil();
    }

    public void getInitShareList(ToastQueryListener<ShareSong> listener) {
        bmobUtil.getInitList(ShareSong.class, "user", listener);
    }

    public void getMoreShareList(int mPage, ToastQueryListener<ShareSong> toastQueryListener) {
        bmobUtil.getMoreList(ShareSong.class, "user", mPage, toastQueryListener);
    }
}
