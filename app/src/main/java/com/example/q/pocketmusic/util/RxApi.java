package com.example.q.pocketmusic.util;

import java.util.concurrent.Callable;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by 81256 on 2018/2/7.
 */

public class RxApi {

    public static <T> Single<T> create(Callable<T> apiCall) {
        return Single.fromCallable(apiCall)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
