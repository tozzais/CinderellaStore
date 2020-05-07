package com.cinderellavip.store.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.cinderellavip.store.R;
import com.cinderellavip.store.listener.OnGetStringListener;


public class CenterDialogUtil {


    private static Dialog cityDialog;




    public static void showTwo(Context context,
                               String title, String content, String btnCancel, String btnSure
            , final OnGetStringListener listener) {
        View messageView = View.inflate(context, R.layout.pop_one_btn2, null);
        cityDialog = DialogUtils.getCenterDialog(context, messageView);
        TextView tv_title = messageView.findViewById(R.id.tv_title);
        TextView tv_content = messageView.findViewById(R.id.tv_content);
        TextView tv_cancel = messageView.findViewById(R.id.tv_cancel);
        TextView tv_sure = messageView.findViewById(R.id.tv_sure);
        tv_title.setText(title);
        tv_content.setText(content);
        tv_cancel.setText(btnCancel);
        tv_sure.setText(btnSure);
        tv_sure.setOnClickListener(v -> {
            listener.getString("1");
            cityDialog.dismiss();
            cityDialog = null;
        });
        tv_cancel.setOnClickListener(v -> {
            listener.getString("0");
            cityDialog.dismiss();
            cityDialog = null;

        });
    }

}
