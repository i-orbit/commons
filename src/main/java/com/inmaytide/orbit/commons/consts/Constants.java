package com.inmaytide.orbit.commons.consts;

/**
 * @author inmaytide
 * @since 2023/5/26
 */
public final class Constants {

    private Constants() {

    }

    /**
     * 数据库表结构中租户字段必填, 但数据无租户信息的情况下填入的默认值
     */
    public static final Long NON_TENANT_ID = 3721L;

    /**
     * 系统配置数据Key: 操作日志保留时间(天)
     */
    public static final String SPK_OP_LOG_RETENTION_TIME_IN_DAYS = "times.days.operation.log.retention";

    /**
     * 系统配置数据Key: 用户密码有效时间(天)
     */
    public static final String SPK_USER_PASS_VALID_IN_DAYS = "times.days.user.password.valid";

}
