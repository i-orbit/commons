package com.inmaytide.orbit.commons.domain.pattern.interptor;

import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.inmaytide.orbit.commons.consts.Is;
import com.inmaytide.orbit.commons.domain.pattern.AuditEntity;
import com.inmaytide.orbit.commons.domain.pattern.TombstoneEntity;
import com.inmaytide.orbit.commons.security.SecurityUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Signature;

import java.sql.SQLException;
import java.time.Instant;
import java.util.Map;

/**
 * @author inmaytide
 * @since 2023/4/6
 */
@Intercepts(@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}))
public class EntityAuditDataInterceptor implements InnerInterceptor {

    @Override
    public void beforeUpdate(Executor executor, MappedStatement ms, Object parameter) throws SQLException {
        if (parameter instanceof AuditEntity params) {
            setAuditFields(params, ms);
        }
        if (parameter instanceof Map<?, ?> params) {
            setAuditFields(params, ms);
        }
    }

    private void setAuditFields(Map<?, ?> parameter, MappedStatement ms) {
        Object et = parameter.getOrDefault(Constants.ENTITY, null);
        if (et instanceof AuditEntity entity) {
            setAuditFields(entity, ms);
        }
    }

    private void setAuditFields(AuditEntity entity, MappedStatement ms) {
        if (ms.getSqlCommandType() == SqlCommandType.INSERT) {
            entity.setCreatedBy(SecurityUtils.getAuthorizedUser().getId());
            entity.setCreatedTime(Instant.now());
            entity.setVersion(1);
            if (entity instanceof TombstoneEntity tombstoneEntity) {
                tombstoneEntity.setDeleted(Is.N);
            }
        }
        if (ms.getSqlCommandType() == SqlCommandType.UPDATE) {
            entity.setModifiedBy(SecurityUtils.getAuthorizedUser().getId());
            entity.setModifiedTime(Instant.now());
        }
    }

}
