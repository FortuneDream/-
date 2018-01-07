package com.example.q.pocketmusic.config;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * Created by 81256 on 2017/12/17.
 */
//类似于桥接的设计方式，把handler注入进来。
public class WeakHandler extends Handler {
    private WeakReference<IHandler> mRef;

    public interface IHandler {
        public void handleMsg(Message msg);
    }

    public WeakHandler(Looper looper, IHandler handler) {
        super(looper);
        mRef = new WeakReference<IHandler>(handler);
    }

    public WeakHandler(IHandler handler) {
        mRef = new WeakReference<IHandler>(handler);
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        IHandler handler = mRef.get();
        if (handler != null && msg != null) {
            handler.handleMsg(msg);
        }
    }
}
