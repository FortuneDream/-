package com.example.q.pocketmusic.view.widget.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.q.pocketmusic.R;

/**
 * Created by 鹏君 on 2017/5/17.
 */
//顶部双选View
public class TopTwoTabView extends LinearLayout {
    private TextView mLeftTv;
    private TextView mRightTv;
    private TopTabListener listener;

    public void setListener(TopTabListener listener) {
        this.listener = listener;
    }

    public  interface TopTabListener{
        void setTopTabCheck(int position);
    }

    public TopTwoTabView(Context context) {
        this(context, null);
    }

    public TopTwoTabView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TopTwoTabView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(getContext(), R.layout.view_top_tab, this);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TopTwoTabView);
        String leftStr = array.getString(R.styleable.TopTwoTabView_Left_Text);
        String rightStr = array.getString(R.styleable.TopTwoTabView_Right_Text);
        array.recycle();
        mLeftTv = getView(R.id.left_tv);
        mRightTv = getView(R.id.right_tv);

        mLeftTv.setText(leftStr);
        mRightTv.setText(rightStr);

        mLeftTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (listener!=null){
                        listener.setTopTabCheck(0);
                    }
            }
        });

        mRightTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (listener!=null){
                        listener.setTopTabCheck(1);
                    }
            }
        });
    }

    //0或者1
    public void setCheck(int position) {
        if (position == 0) {
            mLeftTv.setBackgroundResource(R.drawable.shape_toolbar_tab_pressed);
            mLeftTv.setTextColor(getResources().getColor(R.color.colorPrimary));
            mRightTv.setBackgroundResource(R.drawable.shape_toolbar_tab_normal);
            mRightTv.setTextColor(getResources().getColor(R.color.white));
        } else {
            mRightTv.setBackgroundResource(R.drawable.shape_toolbar_tab_pressed);
            mRightTv.setTextColor(getResources().getColor(R.color.colorPrimary));
            mLeftTv.setBackgroundResource(R.drawable.shape_toolbar_tab_normal);
            mLeftTv.setTextColor(getResources().getColor(R.color.white));
        }
    }

    public <T extends View> T getView(int id) {
        return (T) findViewById(id);
    }


}
