package com.cinderellavip.store;

import android.webkit.JavascriptInterface;

import com.cinderellavip.store.ui.WebViewActivity;
import com.tozzais.baselibrary.util.log.LogUtil;

public class AppJs1 extends Object {
    private WebViewActivity h5Activity;
    public AppJs1(WebViewActivity h5Activity) {
            this.h5Activity = h5Activity;
        }

    @JavascriptInterface
    public void changeSuccess(String data) {
        LogUtil.e(data);
        h5Activity.finish();

    }
    @JavascriptInterface
    public void changeSuccess() {
        LogUtil.e("changeSuccess");
        h5Activity.finish();

    }

    @JavascriptInterface
    public void changeSuccess(Boolean isSuccess) {
        LogUtil.e("changeSuccess"+isSuccess);
        h5Activity.finish();

    }


}
