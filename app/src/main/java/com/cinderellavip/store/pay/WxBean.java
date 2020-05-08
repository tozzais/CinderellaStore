package com.cinderellavip.store.pay;

import com.google.gson.annotations.SerializedName;

public class WxBean {


    @SerializedName("package")
    public String PACKAGE;
    @SerializedName("timestamp")
    public String TIMESTAMP;
    @SerializedName("sign")
    public String SIGN;
    @SerializedName("partnerid")
    public String PARTNERID;

    @SerializedName("appid")
    public String APPID;
    @SerializedName("prepayid")
    public String PREPAYID;
    @SerializedName("noncestr")
    public String NONCESTR;


}
