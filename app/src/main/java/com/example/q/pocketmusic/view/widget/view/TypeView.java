package com.example.q.pocketmusic.view.widget.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.q.pocketmusic.R;

/**
 * Created by 鹏君 on 2016/11/18.
 */

public class TypeView extends LinearLayout {
    private String name;
    private int resourceId;
    private ImageView typeIv;
    private TextView typeTv;

    public TypeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TypeView);
        name = array.getString(R.styleable.TypeView_itemName);
        resourceId = array.getResourceId(R.styleable.TypeView_itemIco, 0);
        array.recycle();

        inflate(getContext(),R.layout.item_type,this);
        typeIv=getView(R.id.type_iv);
        typeTv=getView(R.id.type_tv);

        typeIv.setImageResource(resourceId);
        typeTv.setText(name);
    }

    public <T extends View> T getView(int id) {
        return (T) findViewById(id);
    }
}
