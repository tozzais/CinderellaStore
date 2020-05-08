package com.cinderellavip.store.http;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.cinderellavip.store.global.GlobalParam;
import com.cinderellavip.store.ui.LoginActivity;
import com.google.gson.JsonSyntaxException;
import com.tozzais.baselibrary.util.NetworkUtil;
import com.tozzais.baselibrary.util.progress.LoadingUtils;
import com.tozzais.baselibrary.util.toast.ToastCommom;

import org.greenrobot.eventbus.EventBus;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

public class Response<T> extends Subscriber<T> {

    private Context mContext;
    private boolean mNeedDialog = true; //是否需要对话框
    private boolean mNeedReturn = true; //是否返回
    private boolean mNeedTip = true; //是否需要提示
    private boolean isLoad = true; //是否初始化
    private onCancelRequestListener cancelRequestListener;

    public Response(Context context) {
        this.mContext = context;
    }


    public static final int DIALOG=0;
    public static final int CALLBACK=1;
    public static final int TIP=2;
    public static final int DIALOG_AND_CALLBACK=3;
    public static final int DIALOG_AND_TIP=4;
    public static final int CALLBACK_AND_TIP=5;
    public static final int BOTH=6;
    public Response(Context context, int type) {
        this.mContext = context;
        switch (type){
            case DIALOG:
                //只要加载框 其他都不需要处理 默认就好 类似于登录
                mNeedDialog = false;
                break;
            case CALLBACK:
                mNeedReturn = false;
                break;
            case TIP:
                mNeedTip = false;
                break;
            case DIALOG_AND_CALLBACK:
                mNeedDialog = false;
                mNeedReturn = false;
                break;
            case DIALOG_AND_TIP:
                mNeedTip = false;
                mNeedDialog = false;
                break;
            case CALLBACK_AND_TIP:
                mNeedReturn = false;
                mNeedTip = false;
                break;
            case BOTH:
                mNeedTip = false;
                mNeedDialog = false;
                mNeedReturn = false;
                break;
        }
    }

    public Response(boolean isLoad, Context context) {

        this.isLoad = isLoad;
        this.mContext = context;
        if (isLoad){
            //加载了 需要提示
            mNeedDialog = false;
            mNeedReturn = false;
        }else {
            //没加载需要 返回
            mNeedTip = false;
            mNeedDialog = false;
        }
    }


    /**
     * 此方法现在onNext或者onError之后都会调用
     * 所以一般要处理请求结束时的信息是，需要重写此方法
     * 例如，loading结束时，刷新下拉刷新结果时等…………
     */
    @Override
    public void onCompleted() {
        if (mNeedDialog) {
            LoadingUtils.dismiss();
        }
        mContext = null;
    }

    @Override
    public void onNext(T str) {

        if (str instanceof BaseResult){
            BaseResult base = (BaseResult) str;
            if (200 == base.code ){
                onSuccess(str);
            }else if (20001 == base.code || 20014 == base.code || 20015 == base.code ){
                /**
                 * 单点登录  是首页的时候  都要走newIntent
                 *  不是首页的时候
                 */
                GlobalParam.setUserLogin(false);
//                GlobalParam.setUserId("0");
                LoginActivity.launch((Activity) mContext);
            }else {
                if (!TextUtils.isEmpty(base.message)){
                    if (mNeedReturn && !isLoad){
                        onErrorShow(base.message);
                    }if (mNeedTip){
                        onToast(base.message);
                    }
                }
            }
        }
//        if (str instanceof BaseListResult){
//            BaseListResult base = (BaseListResult) str;
//            if (0 == base.code || "0".equals(base.code)){
//                onSuccess(str);
//            }else if (20000 == base.code || "20000".equals(base.code)){
//                GlobalParam.setUserLogin(false);
//                GlobalParam.setUserId("0");
//                EventBus.getDefault().post(new UpdateMineInfo());
//                SelectLoginWayActivity.launch(true,(Activity) mContext);
//            }else {
//                if (!TextUtils.isEmpty(base.msg)){
//                    if (mNeedReturn && !isLoad){
//                        onErrorShow(base.msg);
//                    }if (mNeedTip){
//                        onToast(base.msg);
//                    }
//                }
//            }
//        }
        onCompleted();
    }


    public void onSuccess(T t){

    }

    public void onErrorShow(String s){

    }
    public void onToast(String s){
        ToastCommom.createToastConfig().ToastShow(mContext,s);
    }




    @Override
    public void onStart() {
        if (mNeedDialog) {
            LoadingUtils.show(mContext);
        }
        LoadingUtils.setOnKeyListener((dialog, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                unsubscribe();
                LoadingUtils.dismiss();
                if (cancelRequestListener != null) {
                    cancelRequestListener.onCancelRequest();
                }
            }
            return false;
        });
    }

    /**
     * 除非非要获取网络错误信息，否则一般不需要重写此方法；
     * 例如：网络400，404，断网，超时，等等…………
     */
    @Override
    public void onError(Throwable e) {
        if (e == null)
            return;
        try {
            String s = "";
            if (!NetworkUtil.isNetworkAvailable(mContext)) {
                  s = "没有网络";
            }else if (e instanceof ConnectException || e instanceof UnknownHostException) {
                s = "连接服务器失败";
            } else if (e instanceof SocketTimeoutException) {
                s = "连接超时";
            } else if (e instanceof HttpException) {
                s = "服务器发生错误";
            } else if (e instanceof JsonSyntaxException) {
                s = "解析失败";
            } else {
                s = e.getMessage();
            }
            if (mNeedReturn && !isLoad){
                //不需要提示的地方显示布局
                onErrorShow(s);
            }if (mNeedTip){
                //默认提示
                onToast(s);
            }
            onCompleted();
            e.printStackTrace();
        } catch (Exception ignored) {

        }

    }



    public interface onCancelRequestListener {
         void onCancelRequest();
    }

}