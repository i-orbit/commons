package com.inmaytide.orbit.commons.utils;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @author inmaytide
 * @since 2023/4/8
 */
public class CodecUtils {

    public static final String RSA_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCAqdUwxEiBO4HVawLDSNfXRX36caZOFwbgTWA0btPvnTNC7hPeoTKePKu7kf5FF7d0RQj2gDr6CNRv+OR1lVCshpe0YMfLaon7NxtAUbV/ffkqD4rTCluIgsE5LGfWhJ9Zy6pvXVJxE4yw+iRALuI9GIuK6Efo/HbYFb6plrVXYQIDAQAB";

    public static final String RSA_PRIVATE_KEY = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAICp1TDESIE7gdVrAsNI19dFffpxpk4XBuBNYDRu0++dM0LuE96hMp48q7uR/kUXt3RFCPaAOvoI1G/45HWVUKyGl7Rgx8tqifs3G0BRtX99+SoPitMKW4iCwTksZ9aEn1nLqm9dUnETjLD6JEAu4j0Yi4roR+j8dtgVvqmWtVdhAgMBAAECgYByZY52Q68rqFsyBGl6dKPkdZcjPDVfrT2h5WoqdHffzmerlL757V1yMAOJTK9Fg2bL7C2h7jWB5qPrwuJuYYpN9R6+jyiGymw+/sfUTkU4nE4WCL390zZYQR/fho8o0LFfY21AHX8vPJtULdC5cQB8HKIaWLodfmOSV+rWwrfboQJBANL/P8KgLWzi2kT66Ji6pETypQEoTO58Psz38s+z+u/U2/jx3/XrprMJ24MiKdqWOgkQjw6CWN9TvbGhhlVPBb0CQQCcGwtsjtWkOemsTcGaBsjnlCIbEF2q0m9zhDnrFDcanIBrpD0UBNwXhII2/kJuTArOL4T7FHvkuNDvyB+jJhh1AkARVxzcV+Gj0zjw9lXrR1t8txxMbg10hA19Nttjqnzy/OFfIjGvukAm8qZxrnsWFcaLPCslnBzFrdInKQkrNaVZAkBtpe1rThSwKuGAXol7OALL7tfZ7K/uOauBF0JbZbKi5YSqlw0zz4oMls2j9QoSUSVR2Dzu4192s35GvmnCfhK1AkAoQGW95ZuFMmsg2x28hWcTTV+kow+LLhCd/gXCbWo2qryipnqSyNmKnRDNm5hmY29fPsuSsJXmU1Dhr8JN5QnQ";


    /**
     * RSA私钥加密
     *
     * @param str 加密字符串
     * @param key 私钥
     * @return 密文
     * @throws Exception 加密过程中的异常信息
     */
    public static String encrypt(String str, String key) throws Exception {
        //base64编码的公钥
        byte[] decoded = Base64.getDecoder().decode(key);
        RSAPrivateKey privateKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return Base64.getEncoder().encodeToString(cipher.doFinal(str.getBytes(StandardCharsets.UTF_8)));
    }

    /**
     * RSA私钥解密
     *
     * @param str 加密字符串
     * @param key 公钥
     * @return 明文
     * @throws Exception 解密过程中的异常信息
     */
    public static String decrypt(String str, String key) throws Exception {
        //64位解码加密后的字符串
        byte[] inputByte = Base64.getDecoder().decode(str);
        //base64编码的私钥
        byte[] decoded = Base64.getDecoder().decode(key);
        RSAPublicKey publicKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        return new String(cipher.doFinal(inputByte));
    }
}
