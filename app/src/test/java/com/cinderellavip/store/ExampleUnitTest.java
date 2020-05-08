package com.cinderellavip.store;

import org.junit.Test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
//        assertEquals(4, 2 + 2);
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("phone", "13166015579");
        hashMap.put("password", "123456");
        System.out.println(getMd5(hashMap));
    }

    public  String getMd5(TreeMap<String, String> keys) {
        StringBuffer sign = new StringBuffer();
        for (Map.Entry<String, String> entry : keys.entrySet()) {
            sign.append(entry.getKey()+"="+entry.getValue()+"&");
        }
        sign.append("secret=241cd2aa2aae01cd2&");
        sign.append("timestamp="+System.currentTimeMillis()/1000);
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(("" + sign).getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            //32位加密
            return buf.toString();
            // 16位的加密
            //return buf.toString().substring(8, 24);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

    }
}