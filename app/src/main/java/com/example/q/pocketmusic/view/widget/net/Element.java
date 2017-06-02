package com.example.q.pocketmusic.view.widget.net;

/**
 * Created by 鹏君 on 2016/9/8.
 */
public class Element {
    public int color;
    public Double direction;
    public float speed;
    public float x = 0;
    public float y = 0;

    public Element(int color, Double direction, float speed){
        this.color = color;
        this.direction = direction;
        this.speed = speed;
    }
}
