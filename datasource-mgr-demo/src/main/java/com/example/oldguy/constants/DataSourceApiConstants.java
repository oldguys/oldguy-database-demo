package com.example.oldguy.constants;

import com.example.oldguy.utils.RsaUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @ClassName: DataSourceApiConstants
 * @Author: ren
 * @Description:
 * @CreateTIme: 2020/2/1 0001 下午 2:42
 **/
@Configuration
public class DataSourceApiConstants {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceApiConstants.class);

    public static PublicKey PUBLIC_KEY;

    public static PrivateKey PRIVATE_KEY;

    @Autowired
    public void initPublicKey(
            @Value("${datasource-api.rsa.public-key}") String publicKey,
            @Value("${datasource-api.rsa.private-key}") String privateKey) throws Exception {
        LOGGER.info("初始化rsa变量");

        PUBLIC_KEY = RsaUtils.getHexPublicKey(publicKey);
        PRIVATE_KEY = RsaUtils.getHexPrivateKey(privateKey);
    }
}
