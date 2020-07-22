package com.cinderellavip.store.util;

public class VersionUtil {
    /**
     * 本地版本是否小于服务器版本
     * @param localVersion
     * @param netVersion
     * @return
     */
    public static  boolean isModify(String localVersion,String netVersion){
        String[] split = localVersion.split("\\.");
        String[] split1 = netVersion.split("\\.");
        if (split1.length != 3){
            return false;
        }
        int i0 = Integer.parseInt(split[0]);
        int i1 = Integer.parseInt(split1[0]);
        int i2 = Integer.parseInt(split[1]);
        int i3 = Integer.parseInt(split1[1]);
        int i4 = Integer.parseInt(split[2]);
        int i5 = Integer.parseInt(split1[2]);
        if (i0<i1 ||
                (i0 == i1 && i2<i3)||
                (i0 == i1 && i2 == i3 && i4<i5)){
            return true;
        }else {
            return false;
        }



    }
}
