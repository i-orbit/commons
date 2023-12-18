package com.inmaytide.orbit.commons.constants;

/**
 * @author inmaytide
 * @since 2023/5/26
 */
public final class Constants {

    private Constants() {

    }

    public static final class Markers {

        public static final String USER_FORCE_LOGOUT = "__force-logout";

        /**
         * 标记一个登录请求是否为免密码登录
         */
        public static final String LOGIN_WITHOUT_PASSWORD = "__without-password";

        /**
         * 标记一些存在但不适用当前情况的相关信息
         */
        public static final String NOT_APPLICABLE = "N/A";

        public static final String LOCAL = "LOCAL";

        public static final String LAN = "LAN";

        public static final Long TREE_ROOT = 0L;

        /**
         * 数据库表结构中租户字段必填, 但数据无租户信息的情况下填入的默认值
         */
        public static final Long NON_TENANT_ID = 3721L;

    }

    public static final class RequestParameters {

        public static final String ACCESS_TOKEN = "access_token";

        public static final String PLATFORM = "platform";

    }

    public final static class HttpHeaderNames {

        public static final String AUTHORIZATION = "Authorization";

        public static final String AUTHORIZATION_PREFIX = "Bearer ";

        public static final String SERVICE_INSTANCE_ID = "Service-Instance-ID";

        public static final String CALL_CHAIN = "Call-Chain";

        public static final String USER_AGENT = "User-Agent";

    }


    public final static class CacheNames {

        public static final String AUTHORIZATION_STORE = "AUTHORIZATION_STORE";

        public static final String ACCESS_TOKEN_STORE = "ACCESS_TOKEN_STORE";

        public static final String REFRESH_TOKEN_STORE = "REFRESH_TOKEN_STORE";

        public static final String TOKEN_TEMPORARY_STORE = "TOKEN_TEMPORARY_STORE";

        public static final String ONLINE_USER = "ONLINE_USER";

        public static final String USER_DETAILS = "USER_DETAILS";

        public static final String TENANT_DETAILS = "TENANT_DETAILS";

    }

    public static final class RabbitMQ {

        /**
         * 交换机名称
         */
        public static final String DIRECT_EXCHANGE = "orbit.mq.direct.exchange";

        /**
         * 延时交换机
         */
        public static final String DIRECT_DELAY_EXCHANGE = "orbit.mq.direct.delay.exchange";

    }

    public static final class SystemPropertyKeys {

        /**
         * 操作日志保留时间(天)
         */
        public static final String OPL_RETENTION_TIME_IN_DAYS = "times.days.operation.log.retention";

        /**
         * 用户密码有效时间(天)
         */
        public static final String USER_PASSWORD_VALID_IN_DAYS = "times.days.user.password.valid";

    }


}
