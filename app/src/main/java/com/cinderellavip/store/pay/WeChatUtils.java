package com.cinderellavip.store.pay;

import android.content.Context;
import android.util.Log;

import com.cinderellavip.store.global.CinderellaStoreApplication;
import com.cinderellavip.store.global.Constant;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tozzais.baselibrary.util.toast.ToastCommom;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by jumpbox on 16/6/2.
 */
public class WeChatUtils {
    private static WeChatUtils weChatUtils;
    private static IWXAPI api;


    public static WeChatUtils getInstance(Context context) {
        if (weChatUtils == null) {
            weChatUtils = new WeChatUtils();
            // 通过WXAPIFactory工厂，获取IWXAPI的实例
            api = WXAPIFactory.createWXAPI(context, null);
            api.registerApp(Constant.WX_APPID);
        }
        return weChatUtils;
    }

    public void wechatPay(WxBean info) {
        if (!api.isWXAppInstalled()) {
            ToastCommom.createToastConfig().ToastShow(CinderellaStoreApplication.mContext, "您还没有安装微信");
            return;
        }
                    try {
                        PayReq req = new PayReq();
                        req.appId = info.APPID;
                        req.partnerId = info.PARTNERID;
                        req.prepayId = info.PREPAYID;
                        req.nonceStr = info.NONCESTR;
                        req.timeStamp = info.TIMESTAMP;
                        req.packageValue = info.PACKAGE;
////                        genPayReq(req);
                        req.sign = info.SIGN;
                        api.sendReq(req);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


    }

    private String getNonceStr(){
        return MD5.getMessageDigest((Math.random()+"").getBytes());
    }


    private void genPayReq(PayReq req) {
        List<NameValuePair> signParams = new LinkedList<>();
        signParams.add(new BasicNameValuePair("appid", req.appId));
        signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
        signParams.add(new BasicNameValuePair("package", req.packageValue));
        signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
        signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
        signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));

        req.sign = genAppSign(signParams);
    }

    private String genAppSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(Constant.WX_APP_SECRET);

        String appSign = MD5.getMessageDigest(sb.toString().getBytes())
                .toUpperCase();
        Log.v("orion", appSign);
        return appSign;
    }

    public void release() {
        api = null;
        weChatUtils = null;
    }

}
