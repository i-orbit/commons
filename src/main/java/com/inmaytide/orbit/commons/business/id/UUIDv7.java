package com.inmaytide.orbit.commons.business.id;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.UUID;

/**
 * @author inmaytide
 * @since 2025/6/25
 */
public class UUIDv7 {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    public static UUID generate() {
        // 获取当前时间戳（48位，毫秒精度）
        long timestamp = Instant.now().toEpochMilli();

        // 生成随机数（80位）
        byte[] randomBytes = new byte[10];
        SECURE_RANDOM.nextBytes(randomBytes);

        // 构建UUIDv7
        long msb = ((timestamp & 0xFFFFFFFFFFFFL) << 16) |
                ((randomBytes[0] & 0xFFL) << 8) |
                (randomBytes[1] & 0xFFL);

        long lsb = ((randomBytes[2] & 0xFFL) << 56) |
                ((randomBytes[3] & 0xFFL) << 48) |
                ((randomBytes[4] & 0xFFL) << 40) |
                ((randomBytes[5] & 0xFFL) << 32) |
                ((randomBytes[6] & 0xFFL) << 24) |
                ((randomBytes[7] & 0xFFL) << 16) |
                ((randomBytes[8] & 0xFFL) << 8) |
                (randomBytes[9] & 0xFFL);

        // 设置版本号(7)和变体号(2)
        msb = (msb & 0xFFFFFFFFFFFF0FFFL) | 0x0000000000007000L;
        lsb = (lsb & 0x3FFFFFFFFFFFFFFFL) | 0x8000000000000000L;

        return new UUID(msb, lsb);
    }

    public static String generateCompact() {
        return generate().toString().replaceAll("-", "");
    }

}
