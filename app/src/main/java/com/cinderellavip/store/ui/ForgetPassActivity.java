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
import com.cinderellavip.store.bean.UserInfo;
import com.cinderellavip.store.global.Constant;
import com.cinderellavip.store.global.GlobalParam;
import com.cinderellavip.store.http.ApiManager;
import com.cinderellavip.store.http.BaseResult;
import com.cinderellavip.store.http.Response;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.CommonUtils;

import java.util.TreeMap;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;

public class ForgetPassActivity extends BaseActivity {
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_pass)
    EditText etPass;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_code)
    TextView tvCode;

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, ForgetPassActivity.class);
        activity.startActivityForResult(intent, Constant.REQUEST_CODE_FORGET_PASS);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_forget_pass;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setBackTitle("忘记密码");

    }

    @Override
    public void loadData() {

    }

    @Override
    public void initListener() {

    }


    private void login() {
        String phone = etPhone.getText().toString().trim();
        String code = etCode.getText().toString().trim();
        String pass = etPass.getText().toString().trim();
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
        if (TextUtils.isEmpty(pass)) {
            tsg("请输入登录密码");
            return;
        }
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("account", phone);
        hashMap.put("password", pass);
        hashMap.put("sms_code", code);
        new RxHttp<BaseResult>().send(ApiManager.getService().getForgetPass(hashMap),
                new Response<BaseResult>(mActivity) {
                    @Override
                    public void onSuccess(BaseResult result) {
                            setResult(phone,pass);
                    }
                });

    }

    private void setResult(String phone, String pass) {
        Intent intent = new Intent();
        intent.putExtra("phone", phone);
        intent.putExtra("pass", pass);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            etPhone.setText(data.getStringExtra("phone"));
            etPass.setText(data.getStringExtra("pass"));
            etPhone.setSelection(etPhone.getText().toString().trim().length());
            etPass.setSelection(etPass.getText().toString().trim().length());

        }
    }




    @OnClick({ R.id.tv_login,R.id.tv_code})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tv_login:
                login();
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
        hashMap.put("type", "9");
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
