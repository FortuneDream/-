package com.example.q.pocketmusic.module.home.convert.comment.convert;

import android.support.v4.app.FragmentManager;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.callback.ToastSaveListener;
import com.example.q.pocketmusic.model.bean.ConvertObject;
import com.example.q.pocketmusic.model.bean.convert.ConvertComment;
import com.example.q.pocketmusic.model.bean.convert.ConvertPost;
import com.example.q.pocketmusic.model.bean.convert.Sound;
import com.example.q.pocketmusic.model.bean.local.LocalConvertSong;
import com.example.q.pocketmusic.model.db.LocalConvertSongDao;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.module.home.convert.comment.convert.piano.PianoFragment;
import com.example.q.pocketmusic.module.home.convert.comment.convert.piano.SongFragment;
import com.example.q.pocketmusic.util.UserUtil;
import com.example.q.pocketmusic.util.common.ToastUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 鹏君 on 2017/4/22.
 */

public class ConvertPresenter extends BasePresenter<ConvertPresenter.IView> {
    private IView activity;
    private PianoFragment pianoFragment;
    private SongFragment songFragment;
    private List<Sound> list;
    private ConvertPost post;
    private LocalConvertSongDao localConvertSongDao;
    private ConvertObject mConvertObject;

    public ConvertPresenter(IView item) {
        attachView(item);
        this.activity = getIViewRef();
        list = new ArrayList<>();
        localConvertSongDao = new LocalConvertSongDao(activity.getAppContext());
    }

    public void initFragment(FragmentManager fm, ConvertObject object) {
        pianoFragment = new PianoFragment();
        songFragment = SongFragment.getInstance(object);
        fm.beginTransaction().add(R.id.content_top, songFragment).commit();
        fm.beginTransaction().add(R.id.content_bottom, pianoFragment).commit();
    }

    public void blank() {
        pianoFragment.blank();
    }

    public void enter() {
        pianoFragment.enter();
    }

    public void boLang() {
        pianoFragment.boLang();
    }

    public void delete() {
        pianoFragment.delete();
    }

    public void setConvertContent() {
        pianoFragment.setConvertContent();
    }

    public void sendConvertComment() {
        if (post == null) {
            return;
        }
        ConvertComment convertComment = new ConvertComment();
        convertComment.setPost(post);
        convertComment.setUser(UserUtil.user);
        convertComment.setContent(pianoFragment.getConvertContent());
        convertComment.save(new ToastSaveListener<String>() {
            @Override
            public void onSuccess(String s) {
                LocalConvertSong localConvertSong = new LocalConvertSong();
                localConvertSong.setContent(pianoFragment.getConvertContent());
                localConvertSong.setDate(dateFormat.format(new Date()));
                localConvertSong.setName(mConvertObject.getName());
                localConvertSongDao.add(localConvertSong);
                ToastUtil.showToast("发表成功,并保存在本地");
                activity.finish();
            }
        });

    }

    public void setPost(ConvertPost post) {
        this.post = post;
    }

    public void setConvertObject(ConvertObject object) {
        this.mConvertObject = object;
    }


    public interface IView extends IBaseView {


    }
}
