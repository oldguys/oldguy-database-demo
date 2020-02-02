package com.example.oldguy.utils;

import org.apache.commons.codec.binary.Hex;
import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @ClassName: RsaUtils
 * @Author: huangrenhao
 * @Description:
 * @CreateTime： 2020/1/6 0006 下午 2:22
 * @Version：
 **/
public class RsaUtils {

    public static KeyPair getKeyPair() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(1024);
        return generator.generateKeyPair();
    }

    /**
     * Base64 ----------------------------------------------
     */

    public static PrivateKey getBase64PrivateKey(String privateKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        byte[] decodedKey = Base64Utils.decode(privateKey.getBytes());
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
        return keyFactory.generatePrivate(keySpec);
    }

    public static PublicKey getBase64PublicKey(String publicKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        byte[] decodedKey = Base64Utils.decode(publicKey.getBytes());
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
        return keyFactory.generatePublic(keySpec);
    }


    public static String encryptToBase64String(String data, PublicKey publicKey) throws Exception {

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        byte[] source = cipher.doFinal(data.getBytes());

        String encryptStr = Base64Utils.encodeToString(source);
        return encryptStr;
    }

    public static String decryptFromBase64String(String data, PrivateKey privateKey) throws Exception {

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        byte[] decodeFromString = Base64Utils.decodeFromString(data);

        String source = new String(cipher.doFinal(decodeFromString));
        return source;
    }


    /**
     * Hex ----------------------------------------------------------
     */

    public static PrivateKey getHexPrivateKey(String privateKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        byte[] decodedKey = Hex.decodeHex(privateKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
        return keyFactory.generatePrivate(keySpec);
    }

    public static PublicKey getHexPublicKey(String publicKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        byte[] decodedKey = Hex.decodeHex(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
        return keyFactory.generatePublic(keySpec);
    }

    public static String decryptFromHexString(String data, PrivateKey privateKey) throws Exception {

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        byte[] decodeFromString = Hex.decodeHex(data);

        String source = new String(cipher.doFinal(decodeFromString));
        return source;
    }

    public static String encryptToHexString(String data, PublicKey publicKey) throws Exception {

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        byte[] source = cipher.doFinal(data.getBytes());

        String encryptStr = Hex.encodeHexString(source);
        return encryptStr;
    }

}
