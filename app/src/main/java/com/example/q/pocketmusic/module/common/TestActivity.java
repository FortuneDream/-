package com.example.q.pocketmusic.module.common;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

/**
 * Created by 鹏君 on 2016/10/11.
 */
//用于测试Activity生命周期
public  abstract class TestActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(getClass().getSimpleName(),"onCreate");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e(getClass().getSimpleName(),"onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(getClass().getSimpleName(),"onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(getClass().getSimpleName(),"onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e(getClass().getSimpleName(),"onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(getClass().getSimpleName(),"onDestroy");
    }
}
