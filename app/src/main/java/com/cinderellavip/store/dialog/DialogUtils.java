package com.cinderellavip.store.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.cinderellavip.store.R;
import com.tozzais.baselibrary.util.ScreenUtil;


public class DialogUtils {

    public static Dialog getBottomDialog(Context context, View view) {
        Dialog dialog = new Dialog(context, R.style.transparentFrameWindowStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.PopupAnimation);

        //解决底部DIalog 有输入框 被键盘挡住
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = ((Activity) context).getWindowManager().getDefaultDisplay().getHeight();
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(wl);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        return dialog;
    }

    public static Dialog getBottomDialog1(Context context, View view) {
        Dialog dialog = new Dialog(context,R.style.transparentFrameWindowStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.PopupAnimation);

        //解决底部DIalog 有输入框 被键盘挡住
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = ((Activity) context).getWindowManager().getDefaultDisplay().getHeight();
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ScreenUtil.getScreenHeight((Activity) context)*2/3;
        dialog.getWindow().setAttributes(wl);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        return dialog;
    }



    public static Dialog getRightDialog(Context context, View view) {
        Dialog dialog = new Dialog(context,R.style.transparentFrameWindowStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.PopupAnimation);

        //解决底部DIalog 有输入框 被键盘挡住
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = ((Activity) context).getWindowManager().getDefaultDisplay().getHeight();
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(wl);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        return dialog;
    }

    public static Dialog getCenterDialog(Context context, View view) {

        return getCenterDialog(context,view,true);
    }

    public static Dialog getCenterDialog(Context context, View view, boolean isOutSideCancel) {

        //设置返回键 无法取消

        Dialog dialog = new Dialog(context,R.style.transparentFrameWindowStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.PopupAnimation);

        //解决底部DIalog 有输入框 被键盘挡住
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        WindowManager.LayoutParams wl = window.getAttributes();
//        wl.x = 0;
//        wl.y = ((Activity) context).getWindowManager().getDefaultDisplay().getHeight();
        wl.gravity = Gravity.CENTER;
        wl.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(wl);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCanceledOnTouchOutside(isOutSideCancel);
        dialog.setCancelable(isOutSideCancel);
        dialog.show();
        return dialog;
    }

    public static Dialog getDialog(Context context, View view) {
        Dialog dialog = new Dialog(context,R.style.transparentFrameWindowStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.PopupAnimation);
        //解决底部DIalog 有输入框 被键盘挡住
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = ((Activity) context).getWindowManager().getDefaultDisplay().getHeight();
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(wl);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        return dialog;
    }



}
