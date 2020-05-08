package com.cinderellavip.store.wxapi;

import android.content.Intent;
import android.os.Bundle;

import com.cinderellavip.store.global.Constant;
import com.cinderellavip.store.pay.PayResultEvent;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class WXPayEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {

    private IWXAPI api;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
//        LogUtils.e(getClass(), resp.errCode + ",微信回调");
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
//            LogUtils.e(getClass(), resp.errCode + "," + resp.errStr);
            finish();
            PayResultEvent resultEvent = new PayResultEvent();
            switch (resp.errCode) {
                case 0:
                    //支付成功
                    this.finish();
                    resultEvent.status = 0;
                    break;
                case -1:
                    //失败
                    this.finish();
                    resultEvent.status = 1;
                    break;
                case -2:
                    //取消
                    this.finish();
                    resultEvent.status = 2;
                    break;

            }
            EventBus.getDefault().post(resultEvent);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        api = WXAPIFactory.createWXAPI(this, Constant.WX_APPID);
        api.handleIntent(getIntent(), this);
    }

}