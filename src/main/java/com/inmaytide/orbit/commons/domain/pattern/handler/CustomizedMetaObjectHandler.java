package com.inmaytide.orbit.commons.domain.pattern.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.inmaytide.orbit.commons.domain.GlobalUser;
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
        GlobalUser user = SecurityUtils.getAuthorizedUser();
        this.strictInsertFill(entity, "createdBy", user::getId, Long.class);
        this.strictInsertFill(entity, "createdTime", Instant::now, Instant.class);
        this.strictInsertFill(entity, "version", () -> 0, Integer.class);
        this.strictInsertFill(entity, "tenant", user::getTenantId, Long.class);
    }

    @Override
    public void updateFill(MetaObject entity) {
        this.strictUpdateFill(entity, "modifiedBy", () -> SecurityUtils.getAuthorizedUser().getId(), Long.class);
        this.strictUpdateFill(entity, "modifiedTime", Instant::now, Instant.class);
    }
}
