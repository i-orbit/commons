package com.inmaytide.orbit.commons.domain.pattern;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.inmaytide.orbit.commons.consts.Is;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;

/**
 * @author inmaytide
 * @since 2023/4/6
 */
@Schema(title = "逻辑删除的实体基类")
public class TombstoneEntity extends AuditEntity {

    @Serial
    private static final long serialVersionUID = 7802160617960166500L;

    @TableLogic(value = "N", delval = "Y")
    private Is deleted;

    public Is getDeleted() {
        return deleted;
    }

    public void setDeleted(Is deleted) {
        this.deleted = deleted;
    }
}
