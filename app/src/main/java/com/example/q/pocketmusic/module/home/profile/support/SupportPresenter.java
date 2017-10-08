package com.example.q.pocketmusic.module.home.profile.support;

import android.content.ClipboardManager;
import android.content.Context;

import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.config.BmobConstant;
import com.example.q.pocketmusic.model.bean.MoneySupport;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.util.BmobUtil;
import com.example.q.pocketmusic.util.common.ToastUtil;

import java.util.List;



/**
 * Created by 鹏君 on 2017/7/8.
 * （￣m￣）
 */

public class SupportPresenter extends BasePresenter<SupportPresenter.IView> {
    private IView activity;
    private int mPage;
    private BmobUtil util;

    public SupportPresenter(IView activity) {
        attachView(activity);
        this.activity = getIViewRef();
        util = new BmobUtil();
    }


    public void getSupportMoneyList(final boolean isRefreshing) {
        if (isRefreshing) {
            mPage = 0;
        }

        util.getMoreList(MoneySupport.class, BmobConstant.BMOB_USER, mPage, new ToastQueryListener<MoneySupport>() {
            @Override
            public void onSuccess(List<MoneySupport> list) {
                activity.setMoneyList(isRefreshing, list);
            }
        });
    }

    public void setPage(int page) {
        this.mPage = page;
    }

    public void getMoreMoneyList() {
        mPage++;
        util.getMoreList(MoneySupport.class, BmobConstant.BMOB_USER, mPage, new ToastQueryListener<MoneySupport>() {
            @Override
            public void onSuccess(List<MoneySupport> list) {
                activity.setMoneyList(false, list);
            }
        });
    }


//    public void pay(boolean isAlipay, final String money, final String content) {
//        if (TextUtils.isEmpty(content) || TextUtils.isEmpty(money)) {
//            ToastUtil.showToast("请输入完整信息");
//            return;
//        }
//
//
//        if (!Pattern.compile("[0-9]*").matcher(money).matches()) {
//            ToastUtil.showToast("请输入整数");
//            return;
//        }
//        if (Double.parseDouble(money) <= 0) {
//            ToastUtil.showToast("请输入大于零的数");
//            return;
//        }
//
//        if (isAlipay && !checkPackageInstalled("com.eg.android.AlipayGphone")) { // 支付宝支付要求用户已经安装支付宝客户端
//            ToastUtil.showToast("请安装支付宝客户端");
//            return;
//        }
//
//        if (!isAlipay && !checkPackageInstalled("com.tencent.mm")) {
//            ToastUtil.showToast("请安装微信客户端");
//            return;
//        }
//
//        if (!isAlipay && !BP.isAppUpToDate(activity.getCurrentContext(), "cn.bmob.knowledge", 8)) {
//            ToastUtil.showToast("监测到本机的支付插件不是最新版,最好进行更新,请先更新插件(无流量消耗)");
//            installApk("bp.db");
//            return;
//        }
//
//        if (!isAlipay) {
//            try {
//                Intent intent = new Intent(Intent.ACTION_MAIN);
//                intent.addCategory(Intent.CATEGORY_LAUNCHER);
//                ComponentName cn = new ComponentName("com.bmob.app.sport",
//                        "com.bmob.app.sport.wxapi.BmobActivity");
//                intent.setComponent(cn);
//                activity.getCurrentContext().startActivity(intent);
//            } catch (Throwable e) {
//                e.printStackTrace();
//            }
//        }
//        BP.pay("捐赠", "支持口袋乐谱的发展", Double.parseDouble(money), isAlipay, new PListener() {
//            @Override
//            public void orderId(String s) {
////                无论支付成功与否,只要成功产生了请求,就返回订单号,请自行保存以便以后查询
//            }
//
//            @Override
//            public void succeed() {
////                支付成功,保险起见请调用查询方法确认结果
//                ToastUtil.showToast("感谢您的支持！");
////                向上取整
//                UserUtil.user.increment(BmobConstant.BMOB_COIN, Math.ceil(Double.parseDouble(money)) * 10);
//                UserUtil.user.update(new ToastUpdateListener() {
//                    @Override
//                    public void onSuccess() {
//                        ToastUtil.showToast(CommonString.ADD_COIN_BASE + Math.ceil(Double.parseDouble(money)) * 10);
//                        MoneySupport moneySupport = new MoneySupport();
//                        moneySupport.setUser(UserUtil.user);
//                        moneySupport.setContent(content);
//                        moneySupport.setMoney(Double.parseDouble(money));
//                        moneySupport.save(new ToastSaveListener<String>() {
//                            @Override
//                            public void onSuccess(String s) {
//
//                            }
//                        });
//                    }
//                });
//            }
//
//            @Override
//            public void fail(int i, String s) {
////                支付失败,有可能是用户中断支付,也有可能是网络问题
//                if (i == -3) {
//                    ToastUtil.showToast("监测到你尚未安装支付插件,无法进行支付,请先安装插件(已打包在本地,无流量消耗),安装结束后重新支付");
//                    installApk("bp.db");
//                } else {
//                    ToastUtil.showToast("支付中断");
//                    LogUtils.e(s);
//                }
//            }
//
//            @Override
//            public void unknow() {
//
//            }
//        });
//    }
//
//    /**
//     * 检查某包名应用是否已经安装
//     */
//    private boolean checkPackageInstalled(String packageName) {
//        try {
//            // 检查是否有支付宝客户端
//            activity.getCurrentContext().getPackageManager().getPackageInfo(packageName, 0);
//            return true;
//        } catch (PackageManager.NameNotFoundException e) {
//            // 没有安装支付宝，跳转到应用市场
//            try {
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse("market://details?id=" + packageName));
//                activity.getCurrentContext().startActivity(intent);
//            } catch (Exception ee) {
//                ee.printStackTrace();
//            }
//        }
//        return false;
//    }
//
//    private void installApk(String s) {
//        installBmobPayPlugin(s);
//    }
//
//    private void installBmobPayPlugin(String fileName) {
//        try {
//            InputStream is = activity.getCurrentContext().getAssets().open(fileName);
//            File file = new File(Environment.getExternalStorageDirectory()
//                    + File.separator + fileName + ".apk");
//            if (file.exists())
//                file.delete();
//            file.createNewFile();
//            FileOutputStream fos = new FileOutputStream(file);
//            byte[] temp = new byte[1024];
//            int i = 0;
//            while ((i = is.read(temp)) > 0) {
//                fos.write(temp, 0, i);
//            }
//            fos.close();
//            is.close();
//
//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                Uri installUri = FileProvider.getUriForFile(activity.getCurrentContext(), activity.getCurrentContext().getPackageName() + ".fileProvider", file);
//                intent.setDataAndType(installUri, "application/vnd.android.package-archive");
//            } else {
//                intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
//            }
//            activity.getCurrentContext().startActivity(intent);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public void copyAlipay() {
       ClipboardManager cm= (ClipboardManager) activity.getAppContext().getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setText("15123100885");
        ToastUtil.showToast("复制成功~");
    }


    public interface IView extends IBaseView {


        void setMoneyList(boolean isRefreshing, List<MoneySupport> list);
    }
}
