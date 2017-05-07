package com.example.q.pocketmusic.module.home.local;

import android.content.Context;
import android.content.Intent;

import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.module.common.BaseActivity;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.lead.LeadSongActivity;
import com.example.q.pocketmusic.module.piano.PianoActivity;

/**
 * Created by Cloud on 2016/11/17.
 */

public class HomeLocalFragmentPresenter extends BasePresenter {
    private Context context;
    private IView fragment;


    public HomeLocalFragmentPresenter(Context context, IView fragment) {
        this.context = context;
        this.fragment = fragment;

    }

    public void enterLeadActivity() {
        Intent intent = new Intent(context, LeadSongActivity.class);
        ((BaseActivity) context).startActivityForResult(intent, LeadSongActivity.REQUEST_LEAD);
    }

    public void enterPianoActivity() {
        context.startActivity(new Intent(context, PianoActivity.class));
    }


    public interface IView extends IBaseView {

    }
}
