package com.example.q.pocketmusic.module.user.notify.suggestion;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by Cloud on 2017/2/19.
 */

public class SuggestionHeader implements RecyclerArrayAdapter.ItemView {
    private TextView headTv;
    private Context context;

    public SuggestionHeader(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(ViewGroup parent) {
        View view=View.inflate(context, R.layout.head_suggestion,null);
        headTv= (TextView) view.findViewById(R.id.head_tv);
        return view;
    }

    @Override
    public void onBindView(View headerView) {
    }
}
