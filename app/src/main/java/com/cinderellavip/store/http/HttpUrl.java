package com.cinderellavip.store.http;

/**
 * Created by jumpbox on 16/5/2.
 */
public interface HttpUrl {

    String server_url = "https://api.huiguniangvip.com/";
    String h5_url = "http://h5.huiguniangvip.com";


    String login = "api/store/login";  //登录
    String get_code = "api/store/sms/send";  //获取验证码
    String code_login = "api/seller/login/sms";  //短信验证码登录
    String forget_pass = "api/seller/forget/pas";  //忘记密码
    String home = "api/seller/index";  //首页



}
