package com.cinderellavip.store.http;



import com.cinderellavip.store.bean.HomeInfo;
import com.cinderellavip.store.bean.UserInfo;

import java.util.TreeMap;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;


/**
 * Created by jumpbox on 16/5/2.
 */
public interface ApiService {


    /**
     * 登录
     * @param
     * @return
     */
    @GET(HttpUrl.login)
    Observable<BaseResult<UserInfo>>
    getLogin(@QueryMap TreeMap<String, String> map);

    @GET(HttpUrl.code_login)
    Observable<BaseResult<UserInfo>>
    getCodeLogin(@QueryMap TreeMap<String, String> map);

    /**
     * 获取验证码
     * @param
     * @return
     */
    @POST(HttpUrl.get_code)
    @FormUrlEncoded
    Observable<BaseResult>
    getCode(@FieldMap TreeMap<String, String> map);

    @GET(HttpUrl.forget_pass)
    Observable<BaseResult>
    getForgetPass(@QueryMap TreeMap<String, String> map);

    @GET(HttpUrl.home)
    Observable<BaseResult<HomeInfo>>
    getHome();




}
