package com.example.helloworld.login;

import android.util.Base64;

import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import java.io.IOException;

/**
 * 使用Base64来保存和获取密码数据
 */
public class Base64Utils {


    /**
     * BASE64解密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String decryptBASE64(String key) {
        int decodetime = 5;//压缩和解压的次数，防止被简单破解
        byte[] bt;
        key = key.trim().replace(" ", "");//去掉空格
        try {
            while (decodetime > 0) {
                key = new String(Base64.decode(key.getBytes(), Base64.DEFAULT));
                decodetime--;
            }

            return key;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * BASE64加密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptBASE64(String key) {
        int decodetime = 5;//压缩和解压的次数，防止被简单破解
        byte[] bt = null;
        key = key.trim().replace(" ", "");//去掉空格
        while (decodetime > 0) {
            bt = key.getBytes();
            key = new String(Base64.encode(bt, Base64.DEFAULT));
            decodetime--;
        }

        return key;
    }
}
