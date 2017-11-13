package com.example.q.pocketmusic.module.song.state;

import android.content.Intent;

import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.data.bean.Song;
import com.example.q.pocketmusic.data.bean.SongObject;
import com.example.q.pocketmusic.data.bean.ask.AskSongComment;
import com.example.q.pocketmusic.data.bean.local.LocalSong;
import com.example.q.pocketmusic.data.bean.share.ShareSong;

import com.example.q.pocketmusic.module.song.SongActivity;
import com.example.q.pocketmusic.module.song.SongActivityPresenter;

/**
 * Created by 鹏君 on 2017/5/3.
 */
//状态控制器
public class SongController {
    private IState state;

    private SongController(IState state) {
        this.state = state;
    }

    public static SongController getInstance(Intent intent, SongActivityPresenter.IView activity) {
        SongObject songObject = (SongObject) intent.getSerializableExtra(SongActivity.PARAM_SONG_OBJECT_SERIALIZABLE);
        Song song = songObject.getSong();
        int isFrom = songObject.getFrom();
        switch (isFrom) {
            case Constant.FROM_SHARE:
                ShareSong shareSong = (ShareSong) intent.getSerializableExtra(SongActivity.SHARE_SONG);
                return new SongController(new ShareState(song, shareSong, activity));//分享
            case Constant.FROM_ASK:
                AskSongComment askSongComment = (AskSongComment) intent.getSerializableExtra(SongActivity.ASK_COMMENT);
                return new SongController(new AskState(song, askSongComment, activity));//求谱
            case Constant.FROM_COLLECTION:
                return new SongController(new CollectionState(song, activity));//收藏
            case Constant.FROM_LOCAL:
                LocalSong localsong = (LocalSong) intent.getSerializableExtra(SongActivity.LOCAL_SONG);
                return new SongController(new LocalState(song, localsong, activity));//本地
            case Constant.FROM_RECOMMEND:
                return new SongController(new RecommendState(song, activity));//推荐
            case Constant.FROM_SEARCH_NET:
                return new SongController(new SearchState(song, activity));//搜索
            case Constant.FROM_TYPE:
                return new SongController(new TypeState(song, activity));//类型
            default:
                return null;
        }
    }


    public void loadPic() {
        state.loadPic();
    }

}
