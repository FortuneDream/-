package com.example.q.pocketmusic.module.song.state;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.Song;
import com.example.q.pocketmusic.model.bean.SongObject;
import com.example.q.pocketmusic.model.bean.ask.AskSongComment;
import com.example.q.pocketmusic.model.bean.local.LocalSong;
import com.example.q.pocketmusic.model.bean.share.ShareSong;
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

    public static SongController getInstance(Intent intent, Context context, SongActivityPresenter.IView activity) {
        SongObject songObject = intent.getParcelableExtra(SongActivity.PARAM_SONG_OBJECT_PARCEL);
        Song song = songObject.getSong();
        int isFrom = songObject.getFrom();
        switch (isFrom) {
            case Constant.FROM_SHARE:
                ShareSong shareSong = (ShareSong) intent.getSerializableExtra(SongActivity.SHARE_SONG);
                return new SongController(new ShareState(song, shareSong, context, activity));//分享
            case Constant.FROM_ASK:
                AskSongComment askSongComment = (AskSongComment) intent.getSerializableExtra(SongActivity.ASK_COMMENT);
                return new SongController(new AskState(song, askSongComment, context, activity));//求谱
            case Constant.FROM_COLLECTION:
                return new SongController(new CollectionState(song, context, activity));//收藏
            case Constant.FROM_LOCAL:
                LocalSong localsong = (LocalSong) intent.getSerializableExtra(SongActivity.LOCAL_SONG);
                return new SongController(new LocalState(song, localsong, context, activity));//本地
            case Constant.FROM_RECOMMEND:
                return new SongController(new RecommendState(song, context, activity));//推荐
            case Constant.FROM_SEARCH_NET:
                return new SongController(new SearchState(song, context, activity));//搜索
            case Constant.FROM_TYPE:
                return new SongController(new TypeState(song, context, activity));//类型
            default:
                return null;
        }
    }


    public void loadPic() {
        state.loadPic();
    }

}
