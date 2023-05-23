package com.inmaytide.orbit.commons.domain.pattern;

import com.inmaytide.orbit.commons.consts.Is;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author inmaytide
 * @since 2023/4/6
 */
@Schema(title = "逻辑删除的实体基类")
public class TombstoneEntity extends AuditEntity {

    private Is deleted;

    public Is getDeleted() {
        return deleted;
    }

    public void setDeleted(Is deleted) {
        this.deleted = deleted;
    }
}
