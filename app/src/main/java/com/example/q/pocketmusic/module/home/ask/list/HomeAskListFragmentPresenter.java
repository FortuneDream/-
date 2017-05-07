package com.example.q.pocketmusic.module.home.ask.list;

import android.content.Context;
import android.content.Intent;

import com.example.q.pocketmusic.module.common.IBaseList;
import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.model.bean.ask.AskSongPost;
import com.example.q.pocketmusic.module.common.BaseFragment;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.home.ask.comment.AskSongCommentActivity;
import com.example.q.pocketmusic.module.home.ask.publish.AskSongActivity;
import com.example.q.pocketmusic.util.BmobUtil;

import java.util.List;

/**
 * Created by Cloud on 2017/1/26.
 */

public class HomeAskListFragmentPresenter extends BasePresenter {
    private Context context;
    private IView fragment;
    private BmobUtil bmobUtil;
    private int mPage;

    public HomeAskListFragmentPresenter(Context context, IView fragment) {
        this.context = context;
        this.fragment = fragment;
        bmobUtil = new BmobUtil();

    }

    //得到帖子列表
    public void getPostList() {
        bmobUtil.getInitList(AskSongPost.class, "user", new ToastQueryListener<AskSongPost>(context, fragment) {
            @Override
            public void onSuccess(List<AskSongPost> list) {
                fragment.setPostList(list);
            }
        });
    }

    //加载更多
    public void getMore() {
        mPage++;
        bmobUtil.getMoreList(AskSongPost.class, "user", mPage, new ToastQueryListener<AskSongPost>(context,fragment) {
            @Override
            public void onSuccess(List<AskSongPost> list) {
                fragment.setPostList(list);
            }
        });

    }

    //跳转到其他人的个人界面
    public void enterOtherProfileActivity(AskSongPost askSongPost) {
//        AskSongPost other = askSongPost.getUser();
//        Intent intent = new Intent(context, OtherProfileActivity.class);
//        intent.putExtra(OtherProfileActivity.PARAM_USER, other);
//        context.startActivity(intent);
    }

    //跳转到评论CommentActivity(
    public void enterCommentActivity(AskSongPost askSongPost) {
        Intent intent = new Intent(context, AskSongCommentActivity.class);
        intent.putExtra(AskSongCommentActivity.PARAM_POST, askSongPost);
        context.startActivity(intent);

    }

    //跳转到AskSongActivity
    public void enterAskSongActivity() {
        Intent intent = new Intent(context, AskSongActivity.class);
        //注意这里使用的是Fragment的方法，而不能用Activity的方法
        ((BaseFragment) fragment).startActivityForResult(intent, AskSongActivity.REQUEST_ASK);
    }

    public void setmPage(int mPage) {
        this.mPage = mPage;
    }


    public interface IView extends IBaseList {
        void setPostList(List<AskSongPost> list);
    }
}
