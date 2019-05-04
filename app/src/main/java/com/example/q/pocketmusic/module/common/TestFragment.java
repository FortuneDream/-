package com.example.q.pocketmusic.module.common;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

/**
 * Created by 鹏君 on 2016/7/21.
 */

//测试生命周期Fragment
public abstract class TestFragment extends BaseFragment {
    @Override
    public void onAttach(Context context) {
        Log.e(getClass().getSimpleName(), "onAttach");
        super.onAttach(context);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.e(getClass().getSimpleName(), "onCreate");
        super.onCreate(savedInstanceState);
    }

    //----------------------------------
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e(getClass().getSimpleName(), "onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e(getClass().getSimpleName(), "onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e(getClass().getSimpleName(), "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(getClass().getSimpleName(), "onResume");
    }

    //--------------------------------------------
    @Override
    public void onPause() {
        super.onPause();
        Log.e(getClass().getSimpleName(), "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e(getClass().getSimpleName(), "onStop");
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e(getClass().getSimpleName(), "onDestroyView");
    }
    //重现视图会跳到onCreateView,onActivityCreated,OnStart,OnResume
    //---------------------------------------


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(getClass().getSimpleName(), "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e(getClass().getSimpleName(), "onDetach");
    }

}
