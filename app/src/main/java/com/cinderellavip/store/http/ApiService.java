package com.cinderellavip.store.http;



import com.cinderellavip.store.bean.UserInfo;

import java.util.TreeMap;

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


}
