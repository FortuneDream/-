package com.example.q.pocketmusic.module.home.local;

import android.content.Intent;

import com.example.q.pocketmusic.module.common.BaseActivity;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.module.lead.LeadSongActivity;
import com.example.q.pocketmusic.module.piano.PianoActivity;

/**
 * Created by Cloud on 2016/11/17.
 */

public class HomeLocalFragmentPresenter extends BasePresenter<HomeLocalFragmentPresenter.IView> {
    private IView fragment;

    public HomeLocalFragmentPresenter() {
        fragment=getIViewRef();
    }

    public void enterLeadActivity() {
        Intent intent = new Intent(fragment.getCurrentContext(), LeadSongActivity.class);
        ((BaseActivity) fragment.getCurrentContext()).startActivityForResult(intent, LeadSongActivity.REQUEST_LEAD);
    }

    public void enterPianoActivity() {
        fragment.getCurrentContext().startActivity(new Intent(fragment.getCurrentContext(), PianoActivity.class));
    }


    public interface IView extends IBaseView {

    }
}
