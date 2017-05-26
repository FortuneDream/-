package com.example.q.pocketmusic.module.home.seek.publish;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.List;

/**
 * Created by 鹏君 on 2017/5/23.
 */

public class AskTagAdapter extends TagAdapter<String> {
    private Context context;

    public AskTagAdapter(List<String> datas, Context context) {
        super(datas);
        this.context = context;
    }

    @Override
    public View getView(FlowLayout parent, int position, String s) {
        TextView tv = (TextView) LayoutInflater.from(context).inflate(R.layout.item_type_tag, parent, false);
        tv.setText(s);
        return tv;
    }
}
