package com.example.q.pocketmusic.module.home.convert.comment.convert;

import android.support.v4.app.FragmentManager;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.model.bean.ConvertObject;
import com.example.q.pocketmusic.model.bean.SongObject;
import com.example.q.pocketmusic.model.bean.convert.ConvertPost;
import com.example.q.pocketmusic.model.bean.convert.Sound;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.module.home.convert.comment.convert.piano.PianoFragment;
import com.example.q.pocketmusic.module.home.convert.comment.convert.piano.SongFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 鹏君 on 2017/4/22.
 */

public class ConvertPresenter extends BasePresenter<ConvertPresenter.IView> {
    private IView activity;
    private PianoFragment pianoFragment;
    private SongFragment songFragment;
    private List<Sound> list;

    public ConvertPresenter(IView item) {
        attachView(item);
        this.activity = getIViewRef();
        list = new ArrayList<>();
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


    public interface IView extends IBaseView {


    }
}
