package com.xwbing.util;

import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.pkcs.RSAPrivateKeyStructure;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * 项目名称: boot-module-demo
 * 创建时间: 2017/11/20 16:26
 * 作者: xiangwb
 * 说明:
 */
public class RSAUtil {

    /**
     * 从文件中加载公钥 测试的时候使用
     *
     * @return
     * @throws Exception
     */
    public static RSAPublicKey loadPublicKey() throws Exception {
        try {
            String filePath = RSAUtil.class.getClassLoader()
                    .getResource("rsa_public_key.pem").getPath();
            InputStream in = new FileInputStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String readLine;
            StringBuilder sb = new StringBuilder();
            while ((readLine = br.readLine()) != null) {
                if (readLine.charAt(0) == '-') {
                    continue;
                } else {
                    sb.append(readLine);
                    sb.append('\r');
                }
            }
            br.close();
            return loadPublicKey(sb.toString());
        } catch (IOException e) {
            throw new Exception("公钥数据流读取错误");
        } catch (NullPointerException e) {
            throw new Exception("公钥输入流为空");
        }
    }

    /**
     * 从字符串中加载公钥
     *
     * @param publicKeyStr 公钥数据字符串
     * @throws Exception 加载公钥时产生的异常
     */
    public static RSAPublicKey loadPublicKey(String publicKeyStr)
            throws Exception {
        try {
            BASE64Decoder base64Decoder = new BASE64Decoder();
            byte[] buffer = base64Decoder.decodeBuffer(publicKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("公钥非法");
        } catch (IOException e) {
            throw new Exception("公钥数据内容读取错误");
        } catch (NullPointerException e) {
            throw new Exception("公钥数据为空");
        }
    }

    /**
     * 从文件中加载私钥 测试的时候使用 私钥文件名
     *
     * @return 是否成功
     * @throws Exception
     */
    public static RSAPrivateKey loadPrivateKey() throws Exception {
        try {
            String filePath = RSAUtil.class.getClassLoader()
                    .getResource("rsa_private_key.pem").getPath();
            InputStream in = new FileInputStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String readLine;
            StringBuilder sb = new StringBuilder();
            while ((readLine = br.readLine()) != null) {
                if (readLine.charAt(0) == '-') {
                    continue;
                } else {
                    sb.append(readLine);
                    sb.append('\r');
                }
            }
            br.close();
            return loadPrivateKey(sb.toString());
        } catch (IOException e) {
            throw new Exception("私钥数据读取错误");
        } catch (NullPointerException e) {
            throw new Exception("私钥输入流为空");
        }
    }

    /**
     * 功能描述： 从字符串中加载私钥
     *
     * @param privateKeyStr
     * @return
     * @throws Exception
     */
    public static RSAPrivateKey loadPrivateKey(String privateKeyStr)
            throws Exception {
        try {
            BASE64Decoder base64Decoder = new BASE64Decoder();
            byte[] priKeyData = base64Decoder.decodeBuffer(privateKeyStr);
            // 读pkcs#8码
            // PKCS8EncodedKeySpec keySpec= new PKCS8EncodedKeySpec(priKeyData);
            // 读pkcs#1码(传统私钥格式)
            RSAPrivateKeyStructure asn1PrivKey = new RSAPrivateKeyStructure(
                    (ASN1Sequence) ASN1Sequence.fromByteArray(priKeyData));
            RSAPrivateKeySpec rsaPrivKeySpec = new RSAPrivateKeySpec(
                    asn1PrivKey.getModulus(), asn1PrivKey.getPrivateExponent());
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return (RSAPrivateKey) keyFactory.generatePrivate(rsaPrivKeySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("私钥非法");
        } catch (IOException e) {
            throw new Exception("私钥数据内容读取错误");
        } catch (NullPointerException e) {
            throw new Exception("私钥数据为空");
        }
    }

    /**
     * 加密过程
     *
     * @param publicKey 公钥 明文数据
     * @return
     * @throws Exception 加密过程中的异常信息
     */
    public static String encrypt(RSAPublicKey publicKey, String data)
            throws Exception {
        if (publicKey == null) {
            throw new Exception("加密公钥为空, 请设置");
        }
        byte[] plainTextData = data.getBytes();
        Cipher cipher;
        try {
            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");// 得到Cipher对象来实现对源数据的RSA加密
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] output = cipher.doFinal(plainTextData);// 执行加密操作
            BASE64Encoder base64Encoder = new BASE64Encoder();
            return base64Encoder.encode(output);// Base64编码成字符串
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此加密算法");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("加密公钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("明文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("明文数据已损坏");
        }
    }

    /***
     * 默认加密方式
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String encrypt(String data) throws Exception {
        return encrypt(loadPublicKey(), data);
    }

    /**
     * 解密过程
     *
     * @param privateKey 私钥 密文数据
     * @return 明文
     * @throws Exception 解密过程中的异常信息
     */
    public static String decrypt(RSAPrivateKey privateKey, String data) throws Exception {
        if (privateKey == null) {
            throw new Exception("解密私钥为空, 请设置");
        }
        BASE64Decoder base64Decoder = new BASE64Decoder();
        byte[] cipherData = base64Decoder.decodeBuffer(data);
        Cipher cipher;
        try {
            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] output = cipher.doFinal(cipherData);
            return new String(output);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此解密算法");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("解密私钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("密文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("密文数据已损坏");
        }
    }

    /**
     * 功能描述： 默认解密方式
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String decrypt(String data) throws Exception {
        return decrypt(loadPrivateKey(), data);
    }

    public static void main(String[] args) throws Exception {
        String en = testJiami();// 密文
        String de = testJiemi(en);// 明文
        System.out.println(de);
    }

    private static String testJiami() throws Exception {
        String plainText = encrypt(loadPublicKey(), "123456");
        System.out.println("加密结果:" + plainText);
        return plainText;
    }

    private static String testJiemi(String str) throws Exception {
        String plainText = decrypt(loadPrivateKey(), str);
        System.out.println("解密结果:" + plainText);
        return plainText;
    }
}
