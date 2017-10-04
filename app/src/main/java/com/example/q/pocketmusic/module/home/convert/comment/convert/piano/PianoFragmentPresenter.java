package com.example.q.pocketmusic.module.home.convert.comment.convert.piano;

import com.example.q.pocketmusic.model.bean.convert.Sound;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.util.MusicUtils;

import java.util.ArrayList;
import java.util.List;

public class PianoFragmentPresenter extends BasePresenter<PianoFragmentPresenter.IView> {
    private IView fragment;
    private MusicUtils mMusic;
    private List<Sound> sounds;

    public PianoFragmentPresenter(final IView fragment) {
        attachView(fragment);
        this.fragment = getIViewRef();
        mMusic = new MusicUtils(fragment.getCurrentContext());
        sounds = new ArrayList<>();
    }


    public void setClickScale(int id) {//播放
        Sound sound = new Sound(id, mMusic.getSoundNote(id), mMusic.getSoundStr(id));
        mMusic.soundPlay(sound);
        sounds.add(sound);
    }

    public String getConvertContent() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < sounds.size(); i++) {
            builder.append(sounds.get(i).getNumber()).append(" ");
        }
        return builder.toString();
    }

    public void blank() {
        Sound sound = new Sound(MusicUtils.BLANK_ID, -1, MusicUtils.BLANK);
        sounds.add(sound);
    }

    public void enter() {
        Sound sound = new Sound(MusicUtils.ENTER_ID, -1, MusicUtils.ENTER);
        sounds.add(sound);
    }

    public void boLang() {
        Sound sound = new Sound(MusicUtils.BO_LANG_ID, -1, MusicUtils.BO_LANG);
        sounds.add(sound);
    }

    public void delete() {
        sounds.remove(sounds.size() - 1);
    }

    public interface IView extends IBaseView {

    }
}
