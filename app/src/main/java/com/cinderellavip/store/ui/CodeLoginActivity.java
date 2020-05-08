package com.cinderellavip.store.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cinderellavip.store.MainActivity;
import com.cinderellavip.store.R;
import com.cinderellavip.store.bean.FinishLogin;
import com.cinderellavip.store.bean.UserInfo;
import com.cinderellavip.store.global.GlobalParam;
import com.cinderellavip.store.http.ApiManager;
import com.cinderellavip.store.http.BaseResult;
import com.cinderellavip.store.http.Response;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.CommonUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.TreeMap;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;

public class CodeLoginActivity extends BaseActivity {
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_code)
    EditText et_code;
    @BindView(R.id.tv_code)
    TextView tvCode;

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, CodeLoginActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login_code;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setBackTitle("商家登录");

    }

    @Override
    public void loadData() {

    }

    @Override
    public void initListener() {

    }


    private void login() {
        String phone = etPhone.getText().toString().trim();
        String code = et_code.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            tsg("请输入手机号");
            return;
        } else if (!CommonUtils.isMobileNO(phone)) {
            tsg("请输入正确的手机号");
            return;
        }
        if (TextUtils.isEmpty(code)) {
            tsg("请输入短信验证码");
            return;
        }
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("account", phone);
        hashMap.put("sms_code", code);
        new RxHttp<BaseResult<UserInfo>>().send(ApiManager.getService().getCodeLogin(hashMap),
                new Response<BaseResult<UserInfo>>(mActivity) {
                    @Override
                    public void onSuccess(BaseResult<UserInfo> result) {
                        GlobalParam.setUserLogin(true);
                        EventBus.getDefault().post(new FinishLogin());
                        MainActivity.launch(mActivity);
                    }
                });


    }



    @OnClick({ R.id.tv_login, R.id.tv_switch, R.id.tv_code})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
                login();
                break;
            case R.id.tv_switch:
                finish();
                break;
            case R.id.tv_code:
                getCode();
                break;

        }
    }

    private void getCode() {
        String phone = etPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            tsg("请输入手机号");
            return;
        } else if (!CommonUtils.isMobileNO(phone)) {
            tsg("请输入正确的手机号");
            return;
        }
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("mobile", phone);
        hashMap.put("type", "8");
        new RxHttp<BaseResult>().send(ApiManager.getService().getCode(hashMap),
                new Response<BaseResult>(mActivity) {
                    @Override
                    public void onSuccess(BaseResult result) {
                        mHandler.sendEmptyMessage(1);
                    }
                });


    }

    private int time = 60;
    private Handler mHandler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            if (time > 0) {
                time--;
                tvCode.setText(time + "s");
                tvCode.setTextColor(getResources().getColor(R.color.grayText));
                mHandler.sendEmptyMessageDelayed(1, 1000);
                tvCode.setEnabled(false);
            } else {
                time = 60;
                tvCode.setTextColor(getResources().getColor(R.color.red));
                tvCode.setText("获取");
                tvCode.setEnabled(true);
            }
            return false;
        }
    });

}
