package com.cinderellavip.store.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.cinderellavip.store.MainActivity;
import com.cinderellavip.store.R;
import com.cinderellavip.store.bean.FinishLogin;
import com.cinderellavip.store.bean.UserInfo;
import com.cinderellavip.store.global.Constant;
import com.cinderellavip.store.global.GlobalParam;
import com.cinderellavip.store.http.ApiManager;
import com.cinderellavip.store.http.BaseResult;
import com.cinderellavip.store.http.CommonInterceptor;
import com.cinderellavip.store.http.HttpUrl;
import com.cinderellavip.store.http.Response;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.CommonUtils;
import com.tozzais.baselibrary.util.sign.SignUtil;

import java.util.TreeMap;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_pass)
    EditText etPass;


    @BindView(R.id.iv_agreement)
    ImageView ivAgreement;

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setNotBackTitle("商家登录");

    }

    @Override
    public void loadData() {

    }

    @Override
    public void initListener() {

    }


    private void login() {
        String phone = etPhone.getText().toString().trim();
        String pass = etPass.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            tsg("请输入手机号");
            return;
        } else if (!CommonUtils.isMobileNO(phone)) {
            tsg("请输入正确的手机号");
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            tsg("请输入登录密码");
            return;
        }
        if (!isSelete){
            tsg("请勾选《用户协议》和《隐私条款》");
            return;
        }
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("account", phone);
        hashMap.put("password", pass);
        new RxHttp<BaseResult<UserInfo>>().send(ApiManager.getService().getLogin(hashMap),
                new Response<BaseResult<UserInfo>>(mActivity) {
                    @Override
                    public void onSuccess(BaseResult<UserInfo> result) {
                        GlobalParam.setUserLogin(true);
                        GlobalParam.setUserToken(result.data.token);
                        GlobalParam.setUserId(result.data.seller_id+"");
                        MainActivity.launch(mActivity);
                    }
                });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUEST_CODE_FORGET_PASS && resultCode == RESULT_OK) {
            etPhone.setText(data.getStringExtra("phone"));
            etPass.setText(data.getStringExtra("pass"));
            etPhone.setSelection(etPhone.getText().toString().trim().length());
            etPass.setSelection(etPass.getText().toString().trim().length());

        }
    }



    private boolean isSelete = false;
    @OnClick({R.id.tv_register_agreement, R.id.tv_privacy, R.id.iv_agreement,R.id.tv_forget, R.id.tv_login, R.id.tv_switch})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_forget:
                ForgetPassActivity.launch(mActivity);
                break;
            case R.id.tv_login:
                login();
                break;
            case R.id.tv_switch:
                CodeLoginActivity.launch(mActivity);
                break;
            case R.id.iv_agreement:
                isSelete = !isSelete;
                ivAgreement.setImageResource(isSelete?R.mipmap.agreement_selete_yes:R.mipmap.agreement_selete_no);
                break;
            case R.id.tv_register_agreement:
                AgreementWebViewActivity.launch(mActivity, HttpUrl.server_url+"api/user/agreements/10");
                break;
            case R.id.tv_privacy:
                AgreementWebViewActivity.launch(mActivity,HttpUrl.server_url+"api/user/agreements/11");
                break;

        }
    }

    @Override
    public void onEvent(Object o) {
        super.onEvent(o);
        if (o instanceof FinishLogin){
            finish();
        }
    }
}
