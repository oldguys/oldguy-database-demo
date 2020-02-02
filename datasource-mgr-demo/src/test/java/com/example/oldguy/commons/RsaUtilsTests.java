package com.example.oldguy.commons;

import com.example.oldguy.utils.RsaUtils;
import org.apache.commons.codec.binary.Hex;
import org.junit.jupiter.api.Test;

import java.security.KeyPair;

/**
 * @ClassName: RsaUtilsTests
 * @Author: ren
 * @Description:
 * @CreateTIme: 2020/2/1 0001 下午 2:33
 **/
public class RsaUtilsTests {

    @Test
    public void test() throws Exception {

        KeyPair keyPair = RsaUtils.getKeyPair();
        String publicKeyStr = Hex.encodeHexString(keyPair.getPublic().getEncoded());
        String privateKeyStr = Hex.encodeHexString(keyPair.getPrivate().getEncoded());

        System.out.println("publicKeyStr:" + publicKeyStr);
        System.out.println("privateKeyStr:" + privateKeyStr);
        System.out.println();

        String source = "root";

        System.out.println("source:" + source);

        String encodeSource = RsaUtils.encryptToHexString(source, RsaUtils.getHexPublicKey(publicKeyStr));
        System.out.println("encode:" + encodeSource);
        String decodeSource = RsaUtils.decryptFromHexString(encodeSource, RsaUtils.getHexPrivateKey(privateKeyStr));
        System.out.println("decode:" + decodeSource);

    }

}
