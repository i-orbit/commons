package com.inmaytide.orbit.commons.domain.pattern.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.inmaytide.orbit.commons.security.SecurityUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.Instant;

/**
 * @author inmaytide
 * @since 2023/7/21
 */
@Component
public class CustomizedMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject entity) {
        SecurityUtils.getAuthorizedUserAllowUnauthorized().ifPresent(user -> {
            this.strictInsertFill(entity, "createdBy", user::getId, Long.class);
            this.strictUpdateFill(entity, "modifiedBy", () -> SecurityUtils.getAuthorizedUser().getId(), Long.class);
            this.strictInsertFill(entity, "tenant", user::getTenantId, Long.class);
        });
        this.strictInsertFill(entity, "createdTime", Instant::now, Instant.class);
        this.strictUpdateFill(entity, "modifiedTime", Instant::now, Instant.class);
        this.strictInsertFill(entity, "version", () -> 0, Integer.class);
    }

    @Override
    public void updateFill(MetaObject entity) {
        SecurityUtils.getAuthorizedUserAllowUnauthorized().ifPresent(user -> {
            this.strictUpdateFill(entity, "modifiedBy", () -> SecurityUtils.getAuthorizedUser().getId(), Long.class);
        });
        this.strictUpdateFill(entity, "modifiedTime", Instant::now, Instant.class);
    }

}
