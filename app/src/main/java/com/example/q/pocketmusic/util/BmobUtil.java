package com.example.q.pocketmusic.util;

import com.example.q.pocketmusic.callback.ToastQueryListener;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;

/**
 * Created by 鹏君 on 2017/4/20.
 */

public class BmobUtil {
    private final int DEFAULT_LIST_NUM = 10;//10个
    private final String DEFAULT_SORT = "-createdAt";//逆序

    //得到帖子列表
    public <T> void getInitList(Class<T> t, String include, ToastQueryListener<T> listener) {
        BmobQuery<T> queryPost = new BmobQuery<>();
        queryPost.order(DEFAULT_SORT);
        queryPost.setLimit(DEFAULT_LIST_NUM);
        if (include != null) {
            queryPost.include(include);
        }
        queryPost.findObjects(listener);
    }

    //加载更多
    public <T> void getMoreList(Class<T> t, String include, int page, ToastQueryListener<T> listener) {
        BmobQuery<T> query = new BmobQuery<>();
        query.order(DEFAULT_SORT);
        query.setLimit(DEFAULT_LIST_NUM);
        query.setSkip(page * DEFAULT_LIST_NUM);
        if (include != null) {
            query.include(include);
        }
        query.findObjects(listener);
    }


    //一对多
    public <T> void getInitListWithEqual(Class<T> t, String include, String line, Object pointer, ToastQueryListener<T> listener) {
        BmobQuery<T> queryComment = new BmobQuery<>();
        queryComment.order(DEFAULT_SORT);
        queryComment.setLimit(DEFAULT_LIST_NUM);
        queryComment.addWhereEqualTo(line, pointer);
        if (include != null) {
            queryComment.include(include);
        }
        queryComment.findObjects(listener);
    }

    public <T> void getMoreListWithEqual(Class<T> t, String include, int page, String line, Object pointer, ToastQueryListener<T> listener) {
        BmobQuery<T> queryComment = new BmobQuery<>();
        queryComment.order(DEFAULT_SORT);
        queryComment.setSkip(page * DEFAULT_LIST_NUM);
        queryComment.setLimit(DEFAULT_LIST_NUM);
        queryComment.addWhereEqualTo(line, pointer);
        if (include != null) {
            queryComment.include(include);
        }
        queryComment.findObjects(listener);
    }

    //多对多
    public <T> void getInitListWithRelated(Class<T> t, String include, String line, BmobPointer pointer, ToastQueryListener<T> listener) {
        BmobQuery<T> queryComment = new BmobQuery<>();
        queryComment.order(DEFAULT_SORT);
        queryComment.setLimit(DEFAULT_LIST_NUM);
        queryComment.addWhereRelatedTo(line, pointer);
        if (include != null) {
            queryComment.include(include);
        }
        queryComment.findObjects(listener);
    }


    //多对多
    public <T> void getMoreListWithRelated(Class<T> t, String include, int page, String line, BmobPointer pointer, ToastQueryListener<T> listener) {
        BmobQuery<T> query = new BmobQuery<>();
        query.order(DEFAULT_SORT);
        query.setLimit(DEFAULT_LIST_NUM);
        query.addWhereRelatedTo(line, pointer);
        query.setSkip(page * 10);
        if (include != null) {
            query.include(include);
        }
        query.findObjects(listener);
    }
}
