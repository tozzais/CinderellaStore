package com.cinderellavip.store.http;

/**
 * Created by jumpbox on 16/5/2.
 */
public interface HttpUrl {


    String server_url = "https://api.huiguniangvip.com/";
    String image_url = server_url+"gp/profile/";

    String login = "api/seller/login/pas";  //用户名登录
    String get_code = "api/store/sms/send";  //获取验证码
    String code_login = "api/seller/login/sms";  //短信验证码登录
    String forget_pass = "api/seller/forget/pas";  //忘记密码
    String home = "api/seller/index";  //首页



}
