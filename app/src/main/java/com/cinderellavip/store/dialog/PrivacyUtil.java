package com.cinderellavip.store.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.cinderellavip.store.R;
import com.cinderellavip.store.http.HttpUrl;
import com.cinderellavip.store.listener.OnDialogClickListener;
import com.cinderellavip.store.ui.AgreementWebViewActivity;
import com.cinderellavip.store.ui.WebViewActivity;


public class PrivacyUtil {


    private static Dialog cityDialog;


    public static void showTwo(Context context,  OnDialogClickListener listener) {
        String str = "温馨提示\n" +
                "在使用我们的产品之前，请务必审慎阅读、并充分理解《服务协议》和《隐私条款》。\n\n" +
                "我们会严格找上述行协议为您提供服务，我们会手机，使用必要信息，同时会采集安全措施。保证您的信息安全，点击“同意，继续使用”" +
                "即表示您已阅读并同意全部条款，可继续使用我们的产品和服务；";
        SpannableString string = new SpannableString(str);
        //设置TextView,可以被当做字符串设置给TextView
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#FF0000"));
        ForegroundColorSpan colorSpan1 = new ForegroundColorSpan(Color.parseColor("#FF0000"));
        string.setSpan(colorSpan, str.indexOf("《服务协议》"), str.indexOf("《服务协议》")+"《服务协议》".length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        string.setSpan(colorSpan1, str.indexOf("《隐私条款》"), str.indexOf("《隐私条款》")+"《隐私条款》".length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);


        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                AgreementWebViewActivity.launch(context,HttpUrl.server_url+"api/user/agreements/1");
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);    //设置是否显示下划线
            }
        };
        string.setSpan(clickableSpan,str.indexOf("《服务协议》"),str.indexOf("《服务协议》")+"《服务协议》".length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);


        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                AgreementWebViewActivity.launch(context, HttpUrl.server_url+"api/user/agreements/2");
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);    //设置是否显示下划线
            }
        };
        string.setSpan(clickableSpan1,str.indexOf("《隐私条款》"),str.indexOf("《隐私条款》")+"《隐私条款》".length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        View messageView = View.inflate(context, R.layout.pop_privacy, null);
        cityDialog = DialogUtils.getCenterDialog(context, messageView,false);
        TextView tv_content = messageView.findViewById(R.id.tv_content);
        TextView tv_sure = messageView.findViewById(R.id.tv_sure);
        TextView tv_cancel = messageView.findViewById(R.id.tv_cancel);
        tv_content.setText(string);
        //要加上这句点击事件才会触发
        tv_content.setMovementMethod(LinkMovementMethod.getInstance());
        tv_sure.setOnClickListener(v -> {
            listener.onSure();
            cityDialog.dismiss();
            cityDialog = null;
        });
        tv_cancel.setOnClickListener(v -> {
            listener.onCancel();
            cityDialog.dismiss();
            cityDialog = null;

        });
    }


}
