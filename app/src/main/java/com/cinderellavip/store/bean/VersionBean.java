package com.cinderellavip.store.bean;

public class VersionBean {
    public String version;
    public String commit;
    public String url;

    @Override
    public String toString() {
        return "VersionBean{" +
                "version='" + version + '\'' +
                ", commit='" + commit + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
