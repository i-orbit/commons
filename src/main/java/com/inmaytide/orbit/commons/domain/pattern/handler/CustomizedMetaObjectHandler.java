package com.inmaytide.orbit.commons.domain.pattern.handler;

import com.baomidou.mybatisplus.core.MybatisPlusVersion;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.inmaytide.orbit.commons.constants.Bool;
import com.inmaytide.orbit.commons.security.SecurityUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;

import java.time.Instant;

/**
 * @author inmaytide
 * @since 2023/7/21
 */
@Component
@ConditionalOnClass(MybatisPlusVersion.class)
public class CustomizedMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject entity) {
        SecurityUtils.getAuthorizedUserAllowUnauthorized().ifPresent(user -> {
            this.strictInsertFill(entity, "createdBy", user::getId, String.class);
            this.strictInsertFill(entity, "updatedBy", user::getId, String.class);
            if (entity.getValue("tenant") == null) {
                this.strictInsertFill(entity, "tenant", user::getTenant, String.class);
            }
        });
        this.strictInsertFill(entity, "createdAt", Instant::now, Instant.class);
        this.strictUpdateFill(entity, "updatedAt", Instant::now, Instant.class);
        this.strictInsertFill(entity, "version", () -> 0, Integer.class);
        this.strictInsertFill(entity, "deleted", () -> Bool.N, Bool.class);
    }

    @Override
    public void updateFill(MetaObject entity) {
        SecurityUtils.getAuthorizedUserAllowUnauthorized().ifPresent(user -> {
            this.strictUpdateFill(entity, "updatedBy", user::getId, String.class);
        });
        this.strictUpdateFill(entity, "updatedAt", Instant::now, Instant.class);
    }


}
