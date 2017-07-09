package com.example.q.pocketmusic.config;

import com.example.q.pocketmusic.util.common.SharedPrefsUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 鹏君 on 2016/8/28.
 */
//静态数据,存放标记，url
public class Constant {
    public final static String APP_ID = "236163bbf4c3be6f2cc44de4405da6eb";
    public final static String PATCH_VERSION = "1";//补丁版本

    //URL
    public final static String BASE_URL = "http://www.qupu123.com";
    public final static String USER_AGENT = "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 UBrowser/5.7.14377.702 Safari/537.36";
    public final static String RECOMMEND_BASE_URL = "http://www.qupu123.com";
    public final static String RECOMMEND_LIST_URL = "http://www.qupu123.com/space/work/6/";//桃李醉春风
    public static String SO_PU_SEARCH = "http://so.sooopu.com/pu/?q=";
    public static String SO_PU_BASE = "http://www.sooopu.com";

    //下载文件夹名
    public final static String FILE_NAME = "YuePuDownload";
    public final static String RECORD_FILE = "YuePuRecord";

    //返回标志
    public final static Integer SUCCESS = 1;
    public final static Integer FAIL = 0;

//    public final static Integer QU_BU = 0;
//    public final static Integer HU_LU_SI = 1;
//    public final static Integer JI_TA = 2;
//    public final static Integer GANG_QIN = 3;
//    public final static Integer SA_KE_SI = 4;
//    public final static Integer ER_HU = 5;
//    public final static Integer GU_ZHENG = 6;
//    public final static Integer DIAN_ZI_QIN = 7;
//    public final static Integer PI_PA = 8;
//    public final static Integer KOU_QIN = 9;
//    public final static Integer RECOMMEND = 10;

    //BmobInfo
    public final static Integer BMOB_INFO_RESET_PASSWORD = 1;//请求重置密码
    public final static Integer BMOB_INFO_LABA = 2;//小喇叭
    public final static Integer BMOB_INFO_PLAN = 3;//开发计划

    //乐器对应
    public final static String[] types = {"全部", "葫芦丝", "吉他", "钢琴", "萨克斯", "二胡", "古筝", "电子琴", "琵琶", "口琴", "桃李醉春风"};
    public final static String[] namesUrl = {"qita/", "hulusi/", "jita/", "gangqin/", "sakesi/", "huqin/", "guzhengguqin/", "dianziqin/", "pipa/", "kouqin/"};


    //Intent Flag:本地/网络
    public final static int NET = 2;
    public final static int LOCAL = 3;

    //Intent Flag:曲谱来自
    public final static int FROM_TYPE = 4;
    public final static int FROM_SEARCH_NET = 5;
    public final static int FROM_COLLECTION = 6;
    public final static int FROM_RECOMMEND = 7;
    public final static int FROM_SHARE = 8;
    public final static int FROM_ASK = 9;
    public final static int FROM_LOCAL = 10;

    //Intent Flag:显示Menu
    public final static int SHOW_ALL_MENU = 1001;//下载，点赞，收藏
    public final static int SHOW_COLLECTION_MENU = 1002;//下载，收藏
    public final static int SHOW_ONLY_DOWNLOAD = 1003;//下载
    public final static int SHOW_NO_MENU = 1004;//显示menu（之后改成显示录音功能menu）

    //请求
    public final static Integer REQUEST_LOGIN = 1;
    public static final int REQUEST_REGISTER = 2;

    //检验邮箱的正则表达式
    public final static String CHECK_EMAIL_REGEX = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";

    //初始头像
    public final static String COMMON_HEAD_IV_URL = "http://bmob-cdn-6553.b0.upaiyun.com/2017/05/04/af2010244002047a80bccc5e1d1a4e81.jpg";//默认头像地址


    //贡献度+
    public final static int ADD_CONTRIBUTION_INIT = 15;//初始
    public final static int ADD_CONTRIBUTION_UPLOAD = 3;//上传
    public final static int ADD_CONTRIBUTION_COMMENT_WITH_PIC = 3;//评论加图
    public final static int ADD_CONTRIBUTION_AGREE = 1;//点赞，赞数比较多时，给予额外加成

    //贡献度-
    public final static int REDUCE_CONTRIBUTION_ASK = 4;//求谱
    public final static int REDUCE_DOWNLOAD = 3;//下载
    public final static int REDUCE_CONTRIBUTION_COLLECTION = 2;//收藏


    //置顶
    public static int sort_value = 1;
    public final static String sort_key = "top_key";

    //求谱类型,最好用Hap映射
    public static final int ji_ta_pu = 0;
    public static final int jian_pu = 1;
    public static final int gang_qin_pu = 2;
    public static final int qi_ta_pu = 3;


    public static final String BMOB_CREATE_AT = "-createdAt";
    public static final String BMOB_USER = "user";


    //增加本地曲谱需要获得顺序
    public static int getSort() {
        int oldSort = SharedPrefsUtil.getInt(sort_key, sort_value);
        SharedPrefsUtil.putInt(sort_key, oldSort + 1);//放入新的值
        return oldSort;
    }
}
