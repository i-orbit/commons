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
import java.util.Objects;

/**
 * @author inmaytide
 * @since 2023/4/6
 */
@Intercepts(@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}))
public class EntityAuditDataInterceptor implements InnerInterceptor {

    @Override
    public void beforeUpdate(Executor executor, MappedStatement ms, Object parameter) throws SQLException {
        if (parameter instanceof Map) {
            Map<String, Object> map = (Map<String, Object>) parameter;
            setAuditFields(map, ms);
        }
    }

    private void setAuditFields(Map<String, Object> parameter, MappedStatement ms) {
        Object et = parameter.getOrDefault(Constants.ENTITY, null);
        if (Objects.nonNull(et)) {
            if (ms.getSqlCommandType() == SqlCommandType.INSERT) {
                if (et instanceof AuditEntity entity) {
                    entity.setCreatedBy(SecurityUtils.getAuthorizedUser().getId());
                    entity.setCreatedTime(Instant.now());
                    entity.setVersion(1);
                }
                if (et instanceof TombstoneEntity entity) {
                    entity.setDeleted(Is.N);
                }
            }

            if (ms.getSqlCommandType() == SqlCommandType.UPDATE) {
                if (et instanceof AuditEntity entity) {
                    entity.setModifiedBy(SecurityUtils.getAuthorizedUser().getId());
                    entity.setCreatedTime(Instant.now());
                }
            }
        }
    }

}
