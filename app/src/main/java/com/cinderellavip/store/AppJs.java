package com.cinderellavip.store;

import android.webkit.JavascriptInterface;

import com.cinderellavip.store.ui.WebViewActivity;
import com.tozzais.baselibrary.util.log.LogUtil;

public class AppJs extends Object {
    private WebViewActivity h5Activity;
    public AppJs(WebViewActivity h5Activity) {
            this.h5Activity = h5Activity;
        }




        @JavascriptInterface
        public void toPay(String data) {
            LogUtil.e(data);
            h5Activity.pay(data);

        }


}
