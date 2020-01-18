package com.xwbing.util;

import com.xwbing.exception.UtilException;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

/**
 * 生成随机码
 *
 * @author xiangwb
 */
public class RadomUtil {

    public static String buildRandom(int length) throws NoSuchAlgorithmException {
        if (length < 1) {
            throw new UtilException("参数异常!!!");
        }
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
            int number = secureRandom.nextInt(str.length());
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        while (true)
            System.out.println(buildRandom(8));
    }
}
