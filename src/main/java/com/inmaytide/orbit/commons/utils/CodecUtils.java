package com.inmaytide.orbit.commons.utils;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

/**
 * @author inmaytide
 * @since 2023/4/8
 */
public final class CodecUtils {

    private CodecUtils() {

    }

    public static final String RSA_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCAqdUwxEiBO4HVawLDSNfXRX36caZOFwbgTWA0btPvnTNC7hPeoTKePKu7kf5FF7d0RQj2gDr6CNRv+OR1lVCshpe0YMfLaon7NxtAUbV/ffkqD4rTCluIgsE5LGfWhJ9Zy6pvXVJxE4yw+iRALuI9GIuK6Efo/HbYFb6plrVXYQIDAQAB";

    public static final String RSA_PRIVATE_KEY = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAICp1TDESIE7gdVrAsNI19dFffpxpk4XBuBNYDRu0++dM0LuE96hMp48q7uR/kUXt3RFCPaAOvoI1G/45HWVUKyGl7Rgx8tqifs3G0BRtX99+SoPitMKW4iCwTksZ9aEn1nLqm9dUnETjLD6JEAu4j0Yi4roR+j8dtgVvqmWtVdhAgMBAAECgYByZY52Q68rqFsyBGl6dKPkdZcjPDVfrT2h5WoqdHffzmerlL757V1yMAOJTK9Fg2bL7C2h7jWB5qPrwuJuYYpN9R6+jyiGymw+/sfUTkU4nE4WCL390zZYQR/fho8o0LFfY21AHX8vPJtULdC5cQB8HKIaWLodfmOSV+rWwrfboQJBANL/P8KgLWzi2kT66Ji6pETypQEoTO58Psz38s+z+u/U2/jx3/XrprMJ24MiKdqWOgkQjw6CWN9TvbGhhlVPBb0CQQCcGwtsjtWkOemsTcGaBsjnlCIbEF2q0m9zhDnrFDcanIBrpD0UBNwXhII2/kJuTArOL4T7FHvkuNDvyB+jJhh1AkARVxzcV+Gj0zjw9lXrR1t8txxMbg10hA19Nttjqnzy/OFfIjGvukAm8qZxrnsWFcaLPCslnBzFrdInKQkrNaVZAkBtpe1rThSwKuGAXol7OALL7tfZ7K/uOauBF0JbZbKi5YSqlw0zz4oMls2j9QoSUSVR2Dzu4192s35GvmnCfhK1AkAoQGW95ZuFMmsg2x28hWcTTV+kow+LLhCd/gXCbWo2qryipnqSyNmKnRDNm5hmY29fPsuSsJXmU1Dhr8JN5QnQ";

    public static final String[] CHARS = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    public static String encrypt(String str) throws Exception {
        return encrypt(str, RSA_PUBLIC_KEY);
    }

    public static String decrypt(String str) throws Exception {
        return decrypt(str, RSA_PRIVATE_KEY);
    }

    public static String encrypt(String str, String key) throws Exception {
        //base64编码的公钥
        byte[] decoded = Base64.getDecoder().decode(key);
        RSAPublicKey publicKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return Base64.getEncoder().encodeToString(cipher.doFinal(str.getBytes(StandardCharsets.UTF_8)));
    }

    public static String decrypt(String str, String key) throws Exception {
        //64位解码加密后的字符串
        byte[] inputByte = Base64.getDecoder().decode(str);
        //base64编码的私钥
        byte[] decoded = Base64.getDecoder().decode(key);
        RSAPrivateKey privateKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(cipher.doFinal(inputByte));
    }

    public static String nameUUID(String name) {
        Objects.requireNonNull(name);
        return UUID.nameUUIDFromBytes(name.getBytes(StandardCharsets.UTF_8)).toString().replaceAll("-", "");
    }

    public static String randomUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String generateRandomString(int len) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < len; i++) {
            int index = random.nextInt(CHARS.length);
            sb.append(CHARS[index]);
        }
        return sb.toString();
    }

    /**
     * 生成一个长度小于等于8的随机字符串
     *
     * @param len 字符串长度 <= 8
     */
    public static String generateRandomCode(int len) {
        if (len > 8 || len < 1) {
            throw new UnsupportedOperationException(String.valueOf(len));
        }
        StringBuilder sb = new StringBuilder();
        String uuid = randomUUID();
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            sb.append(CHARS[x % 0x3E]);
        }
        return sb.toString();
    }
}
