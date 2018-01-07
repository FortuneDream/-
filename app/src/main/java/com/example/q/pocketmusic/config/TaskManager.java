package com.example.q.pocketmusic.config;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledThreadPoolExecutor;


import javax.security.auth.callback.Callback;

/**
 * Created by 81256 on 2017/12/17.
 */

public class TaskManager {
    private static final int DEFAULT_POOL_NUM = 4;
    private static final Executor DEFAULT_EXECUTOR = new ScheduledThreadPoolExecutor(DEFAULT_POOL_NUM);
    private Executor mExecutor;
    private static Handler sMainHandler;
    private boolean mInit;

    public void setExecutor(Executor mExecutor) {
        this.mExecutor = mExecutor;
    }

    public Runnable aync(final Handler handler, final Callable callable, final int what) {
        return new Runnable() {
            @Override
            public void run() {
                if (handler == null) {
                    try {
                        callable.call();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return;
                }
                Message message = handler.obtainMessage(what);
                try {
                    message.obj = callable.call();
                } catch (Exception e) {
                    e.printStackTrace();
                    message.obj = e;
                }
                handler.sendMessage(message);
            }
        };
    }

    public void commit(Handler handler, Callable callable, int what) {
        mExecutor.execute(aync(handler, callable, what));
    }

    public void init(TaskManagerConfig config) {
        mExecutor = config.getExecutor();
        sMainHandler = new Handler(Looper.getMainLooper());
        mInit = true;
    }

    public static void checkInited(TaskManager manager) {
        if (!manager.mInit) {
            throw new IllegalStateException("Task Manager not init");
        }
    }

    public static class TaskManagerConfig {
        private Executor mExecutor;

        public TaskManagerConfig() {
        }

        public Executor getExecutor() {
            return mExecutor;
        }

        public TaskManagerConfig setExecutor(Executor executor) {
            if (executor == null) {
                executor = TaskManager.DEFAULT_EXECUTOR;
            }
            this.mExecutor = executor;
            return this;
        }
    }

}
