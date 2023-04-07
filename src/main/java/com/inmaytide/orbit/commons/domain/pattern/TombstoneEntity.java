package com.inmaytide.orbit.commons.domain.pattern;

import com.inmaytide.orbit.commons.consts.Is;
import io.swagger.annotations.ApiModel;

/**
 * @author inmaytide
 * @since 2023/4/6
 */
@ApiModel("逻辑删除的实体基类")
public class TombstoneEntity extends AuditEntity {

    private Is deleted;

    public Is getDeleted() {
        return deleted;
    }

    public void setDeleted(Is deleted) {
        this.deleted = deleted;
    }
}
