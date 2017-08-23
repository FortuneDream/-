package com.example.q.pocketmusic.module.home.net.type.study;

import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;

/**
 * Created by 鹏君 on 2017/8/20.
 * （￣m￣）
 */

public class StudyPresenter extends BasePresenter<StudyPresenter.IView> {
    private IView activity;

    public StudyPresenter(IView activity) {
        attachView(activity);
        this.activity = getIViewRef();
    }

    interface IView extends IBaseView {

    }
}
