package com.example.q.pocketmusic.view.widget.net;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.q.pocketmusic.util.ScreenUtil;

/**
 * Created by 鹏君 on 2016/12/11.
 */

public class FloatingDraftButton extends FloatingActionButton implements View.OnTouchListener {

    int lastX, lastY;
    int originX, originY;
    final int screenWidth;
    final int screenHeight;


    public FloatingDraftButton(Context context) {
        this(context, null);
    }

    public FloatingDraftButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatingDraftButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        screenWidth = ScreenUtil.getScreenWidth(context);
        screenHeight = ScreenUtil.getContentHeight(context);
        setOnTouchListener(this);//因为设置了监听，所以事件首先交给onTouch，如果返回false才会交给onTouchEvent,如果还没有消费，才会交给onClick处理
    }




    //返回值为false才会传给onTouchEvent，返回true就不会传
    //onTouchEvent默认返回true,除非clickable和LongClickable同时为false，才会返回false
    //在View.setOnClickListener之后，clickable会被设置为true.
    //onTouchEvent的ACTION_UP事件中会触发OnClick
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int ea = event.getAction();
        switch (ea) {
            case MotionEvent.ACTION_DOWN:
                lastX = (int) event.getRawX();// 获取触摸事件触摸位置的原始X坐标
                lastY = (int) event.getRawY();
                originX = lastX;
                originY = lastY;
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = (int) event.getRawX() - lastX;
                int dy = (int) event.getRawY() - lastY;
                int l = v.getLeft() + dx;
                int b = v.getBottom() + dy;
                int r = v.getRight() + dx;
                int t = v.getTop() + dy;
                // 下面判断移动是否超出屏幕
                if (l < 0) {
                    l = 0;
                    r = l + v.getWidth();
                }
                if (t < 0) {
                    t = 0;
                    b = t + v.getHeight();
                }
                if (r > screenWidth) {
                    r = screenWidth;
                    l = r - v.getWidth();
                }
                if (b > screenHeight) {
                    b = screenHeight;
                    t = b - v.getHeight();
                }
                v.layout(l, t, r, b);
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                v.postInvalidate();
                break;
            case MotionEvent.ACTION_UP:
                int distance = (int) event.getRawX() - originX + (int) event.getRawY() - originY;
                if (Math.abs(distance) < 20) {
                    //当变化太小的时候什么都不做，就交给OnTouchEvent处理，执行ACTION_UP事件，从而OnClick执行
                } else {
                    return true;
                }
                break;
        }
        return false;//返回false会为了交给OnTouchEvent处理，OnTouchEvent默认返回true，然后再次传递MOVE事件时，也会先传给OnTouch，再OnTouchEvent，
        //如果这里返回了true，那么将无法传给On

    }

}