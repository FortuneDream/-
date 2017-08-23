package com.example.q.pocketmusic.config;

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


    //BmobInfo
    public final static Integer BMOB_INFO_RESET_PASSWORD = 1;//请求重置密码
    public final static Integer BMOB_INFO_LABA = 2;//小喇叭
    public final static Integer BMOB_INFO_PLAN = 3;//开发计划


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
    public final static int MENU_DOWNLOAD_COLLECTION_AGREE_SHARE = 1001;//下载，点赞，收藏
    public final static int MENU_DOWNLOAD_COLLECTION_SHARE = 1002;//下载，收藏
    public final static int MENU_DOWNLOAD_SHARE = 1003;//下载
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
    public final static int REDUCE_CHANG_NICK_NAME = 10;


    //求谱类型,最好用Hap映射
    public static final int ji_ta_pu = 0;
    public static final int jian_pu = 1;
    public static final int gang_qin_pu = 2;
    public static final int qi_ta_pu = 3;

    //bmobinfo
    private static final int change_password = 1;//改密码
    private static final int splash_flag = 2;//闪屏页问题
    private static final int update_version_info = 3; //版本更新
    private static final int report_content = 4;//
}
