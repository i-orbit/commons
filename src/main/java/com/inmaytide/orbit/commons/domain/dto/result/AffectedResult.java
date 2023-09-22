package com.inmaytide.orbit.commons.domain.dto.result;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author inmaytide
 * @since 2023/4/7
 */
@Schema(title = "批量修改或删除接口返回实体")
public class AffectedResult {

    public static final AffectedResult NOT_AFFECTED = withAffected(0);

    @Schema(title = "操作影响的数据行数")
    private final Integer affected;

    private AffectedResult(Integer affected) {
        this.affected = affected;
    }

    public static AffectedResult withAffected(Integer affected) {
        return new AffectedResult(affected);
    }

    public Integer getAffected() {
        return affected;
    }
}
