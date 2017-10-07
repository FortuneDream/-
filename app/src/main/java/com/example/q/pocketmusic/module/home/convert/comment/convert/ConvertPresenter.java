package com.example.q.pocketmusic.module.home.convert.comment.convert;

import android.support.v4.app.FragmentManager;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.callback.ToastSaveListener;
import com.example.q.pocketmusic.model.bean.ConvertObject;
import com.example.q.pocketmusic.model.bean.convert.ConvertComment;
import com.example.q.pocketmusic.model.bean.convert.ConvertPost;
import com.example.q.pocketmusic.model.bean.convert.Sound;
import com.example.q.pocketmusic.model.bean.convert.ConvertSong;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.module.home.convert.comment.convert.piano.PianoFragment;
import com.example.q.pocketmusic.module.home.convert.comment.convert.piano.SongFragment;
import com.example.q.pocketmusic.util.UserUtil;
import com.example.q.pocketmusic.util.common.ToastUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by 鹏君 on 2017/4/22.
 */

public class ConvertPresenter extends BasePresenter<ConvertPresenter.IView> {
    private IView activity;
    private PianoFragment pianoFragment;
    private SongFragment songFragment;
    private ConvertPost post;
    private ConvertObject mConvertObject;

    public ConvertPresenter(IView item) {
        attachView(item);
        this.activity = getIViewRef();
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

    //发送
    public void sendConvertComment(final String title) {
        if (post == null) {
            return;
        }

        final ConvertComment convertComment = new ConvertComment();
        convertComment.setPost(post);
        convertComment.setTitle(title);
        convertComment.setUser(UserUtil.user);
        convertComment.setContent(pianoFragment.getConvertContent());
        convertComment.save(new ToastSaveListener<String>() {
            @Override
            public void onSuccess(String s) {
                BmobRelation relation = new BmobRelation();
                relation.add(convertComment);
                UserUtil.user.setConverts(relation);
                UserUtil.user.save(new ToastSaveListener() {
                    @Override
                    public void onSuccess(Object o) {

                    }
                });
                ConvertSong convertSong = new ConvertSong();
                convertSong.setContent(pianoFragment.getConvertContent());
                convertSong.setName(title);
                convertSong.setUser(UserUtil.user);
                convertSong.save(new ToastSaveListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        ToastUtil.showToast("发表成功,并保存");
                        activity.finish();
                    }
                });
            }
        });

    }

    public void setPost(ConvertPost post) {
        this.post = post;
    }

    public void setConvertObject(ConvertObject object) {
        this.mConvertObject = object;
    }

    //保存
    public void keepConvertSong() {
        ConvertSong song = new ConvertSong();
        String content = pianoFragment.getConvertContent();
        if (content.length() <= 0) {
            activity.finish();
            return;
        }
        song.setContent(content);
        song.setUser(UserUtil.user);
        song.setName(mConvertObject.getTitle() + new Date());
        song.save(new ToastSaveListener<String>() {
            @Override
            public void onSuccess(String s) {
                activity.finish();
            }
        });
    }


    public interface IView extends IBaseView {


    }
}
