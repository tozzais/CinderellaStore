package com.cinderellavip.store.global;

import android.os.Environment;

/**
 * 全部的常量类。
 * Created by Administrator on 2017/4/15.
 */

public class Constant {

    //图片地址
    public static final String PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+"/hk_fight/image";
    public static final String ROOT_PATH = Environment.getExternalStorageDirectory() + "/hk_fight";
    public static final String cacheDirPath = Environment
            .getExternalStorageDirectory() + "/hk_fight";


    public static String user_login = "hk_user_login";
    public static String search_history = "hk_search_history";
    public static String user_id = "hk_user_id";
    public static String user_nickname = "hk_user_nickname";
    public static String user_token = "hk_user_token_store";
    public static String user_bean_string = "hk_user_bean_string";
    public static String user_first_use = "hk_user_first_use_store";
    public static String user_login_finish = "hk_user_login_finish";
    public static String user_agree_privacy = "hk_user_agree_privacy";
    public static String user_auth = "hk_user_auth";
    public static String user_number = "hk_user_number";
    public static String user_name = "hk_user_name";



    //微信相关
    public static String SMALL_APPLICATION_ID = "gh_269043eeeee4";
    public static String WX_APPID = "wxa47d5e47b775478e";
    public static String WX_APP_SECRET = "0338afaaaacfdcde4e778cc2df841df4";

    //年度额度查询地址
    public static String H5_QUOTA = "https://app.singlewindow.cn/ceb2pubweb/sw/personalAmount";
//    public static String H5_QUOTA = "http://www.ah12377.cn/newsDetail/151";


    public static final int ALL = 0;
    public static final int CANCEL = 1;
    public static final int UNPAY = 2;
    public static final int STOCKING = 3;
    public static final int UNSEND = 4;
    public static final int UNRECEIVE = 5;
    public static final int EVALUATION = 6;
    public static final int FINISH = 7;
    public static final int RETURNING = 8;
    public static final int RETURNED = 9;


    public static final int MENTION = 0; //自提
    public static final int LOGISTICS = 1; //物流


}
