package com.example.q.pocketmusic.module.home.convert.comment;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;

import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.callback.ToastSaveListener;
import com.example.q.pocketmusic.callback.ToastUpdateListener;
import com.example.q.pocketmusic.config.BmobConstant;
import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.ConvertObject;
import com.example.q.pocketmusic.model.bean.MyUser;
import com.example.q.pocketmusic.model.bean.bmob.Gift;
import com.example.q.pocketmusic.model.bean.convert.ConvertComment;
import com.example.q.pocketmusic.model.bean.convert.ConvertPost;
import com.example.q.pocketmusic.model.bean.convert.ConvertPostPic;
import com.example.q.pocketmusic.model.bean.convert.ConvertSong;
import com.example.q.pocketmusic.model.flag.Text;
import com.example.q.pocketmusic.module.common.BaseActivity;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.module.home.convert.comment.convert.ConvertActivity;
import com.example.q.pocketmusic.module.home.profile.convert.temporary.ProfileTemporaryConvertModel;
import com.example.q.pocketmusic.util.UserUtil;
import com.example.q.pocketmusic.util.common.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;


public class ConvertCommentActivityPresenter extends BasePresenter<ConvertCommentActivityPresenter.IView> {
    private IView activity;
    private int mPage;
    private String content;
    private ProfileTemporaryConvertModel profileTemporaryConvertModel;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ConvertPost getPost() {
        return post;
    }

    private ConvertCommentModel convertCommentModel;
    private ConvertPost post;

    public ConvertCommentActivityPresenter(IView activity) {
        attachView(activity);
        this.activity = getIViewRef();
        this.mPage = 0;
        convertCommentModel = new ConvertCommentModel();
        profileTemporaryConvertModel = new ProfileTemporaryConvertModel();
    }

    public void getMoreCommentList() {
        mPage++;
        getCommentList(false);
    }

    public void getCommentList(final boolean isRefreshing) {
        if (isRefreshing) {
            mPage = 0;
        }
        convertCommentModel.getList(post, mPage, new ToastQueryListener<ConvertComment>() {
            @Override
            public void onSuccess(List<ConvertComment> list) {
                activity.setCommentList(isRefreshing, list);
            }
        });

    }

    public void setPost(ConvertPost convertPost) {
        this.post = convertPost;
    }

    public void enterConvertActivity(ConvertObject convertObject) {
        Intent intent = new Intent(activity.getCurrentContext(), ConvertActivity.class);
        intent.putExtra(ConvertActivity.PARAM_CONVERT_OBJECT, convertObject);
        intent.putExtra(ConvertActivity.PARAM_CONVERT_POST, post);
        ((BaseActivity) activity.getCurrentContext()).startActivityForResult(intent, ConvertCommentActivity.REQUEST_TITLE);
    }

    //发送
    public void sendConvertComment(final String title, final String content) {
        if (post == null) {
            return;
        }
        if (TextUtils.isEmpty(title)) {
            ToastUtil.showToast("请输入标题");
            return;
        }
        if (content == null) {
            ToastUtil.showToast("你还没有编写转谱哦~");
            return;
        }
        activity.showLoading(true);
        post.increment(BmobConstant.BMOB_COMMENT_NUM, 1);//增加回复量
        post.update(new ToastUpdateListener(activity) {
            @Override
            public void onSuccess() {
                final ConvertComment convertComment = new ConvertComment();//保存评论
                convertComment.setPost(post);
                convertComment.setTitle(title);
                convertComment.setUser(UserUtil.user);
                convertComment.setContent(content);
                convertComment.save(new ToastSaveListener<String>(activity) {
                    @Override
                    public void onSuccess(String s) {
                        BmobRelation relation = new BmobRelation();
                        relation.add(convertComment);
                        UserUtil.user.setConverts(relation);//加入我的转谱
                        UserUtil.user.update(new ToastUpdateListener(activity) {
                            @Override
                            public void onSuccess() {
                                ConvertSong convertSong = new ConvertSong();
                                convertSong.setContent(content);
                                convertSong.setName(title);
                                convertSong.setUser(UserUtil.user);//我的临时转谱
                                convertSong.save(new ToastSaveListener<String>(activity) {
                                    @Override
                                    public void onSuccess(String s) {
                                        Gift gift = new Gift();
                                        gift.setGet(false);
                                        gift.setContent("有小伙伴看了你的转谱");//发礼物
                                        gift.setCoin(post.getCoin());
                                        gift.setUser(post.getUser());
                                        gift.save(new ToastSaveListener<String>() {
                                            @Override
                                            public void onSuccess(String s) {
                                                ToastUtil.showToast("发表成功");
                                                activity.showLoading(false);
                                                activity.sendCommentResult();
                                            }
                                        });
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });
    }

    public void getConvertObject() {
        activity.showLoading(true);
        BmobQuery<ConvertPostPic> query = new BmobQuery<>();
        query.addWhereEqualTo("post", new BmobPointer(post));
        query.findObjects(new ToastQueryListener<ConvertPostPic>(activity) {
            @Override
            public void onSuccess(List<ConvertPostPic> list) {
                activity.showLoading(false);
                List<String> ivs = new ArrayList<String>();
                for (int i = 0; i < list.size(); i++) {
                    ivs.add(list.get(i).getUrl());
                }
                ConvertObject object = new ConvertObject(post.getTitle(), ivs, Constant.NET);
                enterConvertActivity(object);
            }
        });
    }

    public void checkUserHasConsumeCoin(final ConvertComment item) {
        BmobQuery<ConvertComment> query = new BmobQuery<>();
        query.addWhereRelatedTo("converts", new BmobPointer(UserUtil.user));//在user表的converts找user
        query.findObjects(new ToastQueryListener<ConvertComment>() {
            @Override
            public void onSuccess(List<ConvertComment> list) {
                boolean isConsume = false;
                for (ConvertComment other : list) {
                    if (other.getObjectId().equals(item.getObjectId())) {
                        //已经点赞
                        isConsume = true;
                        break;
                    }
                }

                if (isConsume) {
                    activity.alertCompleteConvertSongDialog(item);
                } else {
                    activity.alertCoinDialog(item, post.getCoin());
                }

            }
        });
    }

    //消费硬币，添加关联
    public void performConsume(final ConvertComment item, final int coin) {
        activity.showLoading(true);
        ToastUtil.showToast("稍等片刻");
        //已经购买的用户
        BmobRelation relation1 = new BmobRelation();
        relation1.add(item);
        UserUtil.user.setConverts(relation1);
        UserUtil.user.update(new ToastUpdateListener(activity) {
            @Override
            public void onSuccess() {
                item.increment("checkNum", 1);//查看数+1
                item.update(new ToastUpdateListener(activity) {
                    @Override
                    public void onSuccess() {
                        UserUtil.increment(-coin, new ToastUpdateListener(activity) {
                            @Override
                            public void onSuccess() {
                                activity.showLoading(false);
                                activity.alertCompleteConvertSongDialog(item);
                            }
                        });
                    }
                });
            }
        });
    }

    //获得暂存列表
    public void getTemporaryConvertList() {
        profileTemporaryConvertModel.getAllList(new ToastQueryListener<ConvertSong>() {
            @Override
            public void onSuccess(List<ConvertSong> list) {
                activity.showListDialog(list);
            }
        });
    }


    interface IView extends IBaseView {

        void setCommentList(boolean isRefreshing, List<ConvertComment> list);

        void alertCompleteConvertSongDialog(ConvertComment item);

        void alertCoinDialog(ConvertComment item, int coin);

        void showListDialog(List<ConvertSong> list);

        void sendCommentResult();
    }
}
