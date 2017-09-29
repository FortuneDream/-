package com.example.q.pocketmusic.util;

import com.example.q.pocketmusic.R;

/**
 * Created by 鹏君 on 2017/7/13.
 * （￣m￣）
 */

public class InstrumentFlagUtil {

    public final static int FLAG_QU_BU = 0;
    public final static int FLAG_HU_LU = 1;
    public final static int FLAG_JI_TA = 2;
    public final static int FLAG_GANG_QIN = 3;
    public final static int FLAG_SA_KE_SI = 4;
    public final static int FLAG_ER_HU = 5;
    public final static int FLAG_GU_ZHENG = 6;
    public final static int FLAG_DIAN_ZI_QIN = 7;
    public final static int FLAG_PI_PA = 8;
    public final static int FLAG_KOU_QIN = 9;
    public final static int FLAG_CHANG_DI = 10;
    public static final int FLAG_DI_XIAO = 11;
    public static final int FLAG_SHOU_FENG_QIN = 12;
    public static final int FLAG_TI_QIN = 13;
    public static final int FLAG_TONG_GUAN = 14;
    public static final int FLAG_YANG_QIN = 15;

    //学习URL
    public final static String WU_XIAN_PU = "https://jingyan.baidu.com/article/f3e34a1266e194f5eb6535f4.html";
    public final static String JIAN_PU = "https://jingyan.baidu.com/article/0bc808fc9f0cf61bd485b9cf.html";
    public final static String JI_TA_PU = "https://jingyan.baidu.com/article/363872ecf72c7e6e4ba16fca.html";

    private final static int[] flags = {
            FLAG_QU_BU, FLAG_HU_LU, FLAG_JI_TA, FLAG_GANG_QIN,
            FLAG_SA_KE_SI, FLAG_ER_HU, FLAG_GU_ZHENG, FLAG_DIAN_ZI_QIN,
            FLAG_PI_PA, FLAG_KOU_QIN, FLAG_CHANG_DI, FLAG_DI_XIAO,
            FLAG_SHOU_FENG_QIN, FLAG_TI_QIN, FLAG_TONG_GUAN, FLAG_YANG_QIN};

    private final static int[] ids = {
            R.id.type_quanbu_tv, R.id.type_hulu_tv, R.id.type_jita_tv, R.id.type_gangqin_tv,
            R.id.type_sakesi_tv, R.id.type_erhu_tv, R.id.type_guzheng_tv, R.id.type_dianziqin_tv,
            R.id.type_pipa_tv, R.id.type_kouqin_tv, R.id.type_changdi_tv, R.id.type_dixiao_tv,
            R.id.type_shoufengqin_tv, R.id.type_tiqin_tv, R.id.type_tongguan_tv, R.id.type_yangqin_tv};

    private final static int[] topDrawable = {
            R.drawable.iv_top_quanbu, R.drawable.iv_top_hulusi, R.drawable.iv_top_jita, R.drawable.iv_top_gangqin,
            R.drawable.iv_top_sakesi, R.drawable.iv_top_erhu, R.drawable.iv_top_guzheng, R.drawable.iv_top_dianziqin,
            R.drawable.iv_top_pipa, R.drawable.iv_top_kouqin, R.drawable.iv_top_quanbu, R.drawable.iv_top_quanbu,
            R.drawable.iv_top_quanbu, R.drawable.iv_top_quanbu, R.drawable.iv_top_quanbu, R.drawable.iv_top_quanbu};
    private final static String[] studyUrls = {JIAN_PU, JIAN_PU, JI_TA_PU, WU_XIAN_PU,
            WU_XIAN_PU, JIAN_PU, JIAN_PU, WU_XIAN_PU,
            JIAN_PU, JIAN_PU, JIAN_PU, JIAN_PU,
            WU_XIAN_PU, WU_XIAN_PU, WU_XIAN_PU, JIAN_PU};

    //乐器对应
    public final static String[] typeNames = {
            "全部", "葫芦丝", "吉他", "钢琴",
            "萨克斯", "二胡", "古筝", "电子琴",
            "琵琶", "口琴", "长笛", "笛萧",
            "手风琴", "提琴", "铜管", "扬琴"};
    public final static String[] namesUrl = {
            "qita/", "hulusi/", "jita/", "gangqin/",
            "sakesi/", "huqin/", "guzhengguqin/", "dianziqin/",
            "pipa/", "kouqin/", "changdi/", "dixiao/",
            "shoufengqin/", "tiqin/", "tongguan/", "yangqin/"};


    private InstrumentFlagUtil() {

    }

    public static void init() {

    }


    //通过id得到flag
    public static int getFlag(int id) {
        for (int i = 0; i < ids.length; i++) {
            if (id == ids[i]) {
                return i;
            }
        }
        return 0;
    }

    public static int getTopDrawableResource(int flag) {
        return topDrawable[flag];
    }

    public static String getUrl(int flag) {
        return namesUrl[flag];
    }

    public static String getTypeName(int flag) {
        return typeNames[flag];
    }

    public static String getStudyUrl(int flag) {
        return studyUrls[flag];
    }

    ;
}
