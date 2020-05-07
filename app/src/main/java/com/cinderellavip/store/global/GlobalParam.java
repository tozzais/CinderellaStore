package com.cinderellavip.store.global;


import com.tozzais.baselibrary.util.SharedPreferencesUtil;


/**
 * Created by jumpbox on 16/4/19.
 */
public class GlobalParam {





    //是否使用
    public static void setFirstUse(boolean firstUse) {
        SharedPreferencesUtil.saveBooleanData(CinderellaStoreApplication.mContext, Constant.user_first_use, firstUse);
    }
    public static boolean getFirstUse() {
        return SharedPreferencesUtil.getBooleanData(CinderellaStoreApplication.mContext, Constant.user_first_use,false);
    }



}
