package com.mlnx.common.utils;


import java.security.KeyPair;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import cn.hutool.crypto.digest.HMac;
import cn.hutool.crypto.digest.HmacAlgorithm;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;


/**
 * 数据加密辅助类(默认编码UTF-8)
 *
 * @author ShenHuaJie
 * @since 2011-12-31
 */
public final class SecurityUtil {
    private SecurityUtil() {
    }

    public static final String CHARSET = "UTF-8";

    //===============================转码============================//

    /**
     * BASE64解码
     *
     * @param key
     * @return
     */
    public static final byte[] decryptBASE64(String key) {
        try {
            return Base64.decode(key);
        } catch (Exception e) {
            throw new RuntimeException("解密错误，错误信息：", e);
        }
    }

    /**
     * BASE64编码
     *
     * @param key
     * @return
     */
    public static final String encryptBASE64(byte[] key) {
        try {
            return Base64.encode(key);
        } catch (Exception e) {
            throw new RuntimeException("加密错误，错误信息：", e);
        }
    }

    //=============================摘要指纹========================//

    /**
     * 基于MD5算法的单向加密
     *
     * @param strSrc 明文
     * @return 返回密文
     */
    public static final String encryptMd5(String strSrc) {
        String outString = null;
        try {
            Digester digester = new Digester(DigestAlgorithm.MD5);
            outString = encryptBASE64(digester.digest(strSrc));
        } catch (Exception e) {
            throw new RuntimeException("加密错误，错误信息：", e);
        }
        return outString;
    }

    /**
     * SHA加密
     *
     * @param data
     * @return
     */
    public static final String encryptSHA(String data) {
        try {
            Digester digester = new Digester(DigestAlgorithm.SHA256);
            return encryptBASE64(digester.digest(data));
        } catch (Exception e) {
            throw new RuntimeException("加密错误，错误信息：", e);
        }
    }

    /**
     * HMAC加密
     *
     * @param data
     * @param key
     * @return
     */
    public static final String encryptHMAC(String data, byte[] key) {
        try {
            HMac mac = new HMac(HmacAlgorithm.HmacMD5, key);
            return encryptBASE64(mac.digest(data));
        } catch (Exception e) {
            throw new RuntimeException("加密错误，错误信息：", e);
        }
    }


    //===========================对称加密AES==============================//

    /**
     * 获取aes的私钥
     *
     * @return
     */
    public static final byte[] aesPrivateKey() {
        return SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded();
    }


    /**
     * 数据解密，算法（AES）
     *
     * @param cryptData 加密数据
     * @param key
     * @return 解密后的数据
     */
    public static final byte[] decryptAes(String cryptData, byte[] key) {
        try {
            // 把字符串解码为字节数组，并解密
            AES aes = SecureUtil.aes(key);
            return aes.decrypt(decryptBASE64(cryptData));
        } catch (Exception e) {
            throw new RuntimeException("解密错误，错误信息：", e);
        }
    }

    /**
     * 数据解密，算法（AES）
     *
     * @param cryptData 加密数据
     * @param key
     * @return 解密后的数据
     */
    public static final String decryptAesStr(String cryptData, byte[] key) {
        return StrUtil.str(decryptAes(cryptData, key), CHARSET);
    }

    /**
     * 数据加密，算法（AES）
     *
     * @param data 要进行加密的数据
     * @param key
     * @return 加密后的数据
     */
    public static final String encryptAes(String data, byte[] key) {
        String encryptedData = null;
        try {
            // 加密，并把字节数组编码成字符串
            AES aes = SecureUtil.aes(key);
            encryptedData = encryptBASE64(aes.encrypt(data.getBytes()));
        } catch (Exception e) {
            throw new RuntimeException("加密错误，错误信息：", e);
        }
        return encryptedData;
    }


    //==========================非对称加密RSA==============================//

    /**
     * 数据加密，算法（RSA）
     *
     * @param data       数据
     * @param privateKeyBase64
     * @return 加密后的数据
     */
    public static final String encryptRSAPrivate(byte[] data, String privateKeyBase64) {

        RSA rsa = new RSA(privateKeyBase64, null);
        return rsa.encryptBase64(data, KeyType.PrivateKey);
    }

    /**
     * 数据解密，算法（RSA）
     *
     * @param cryptData 加密数据
     * @param publicKeyBase64
     * @return 解密后的数据
     */
    public static final String decryptRSAPublic(String cryptData, String publicKeyBase64) {
        RSA rsa = new RSA(null, publicKeyBase64);
        return rsa.decryptStr(cryptData, KeyType.PrivateKey);
    }

    /**
     * 获取公私钥加密对
     * @return
     */
    public static KeyPair generateRsaKeyPair() {
        return SecureUtil.generateKeyPair("RSA", 1024);
    }

    public static void main(String[] args) throws Exception {
    }
}
