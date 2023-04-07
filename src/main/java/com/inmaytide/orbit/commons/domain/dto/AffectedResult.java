package com.inmaytide.orbit.commons.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author inmaytide
 * @since 2023/4/7
 */
@ApiModel("批量修改或删除接口返回实体")
public class AffectedResult {

    @ApiModelProperty("操作影响的数据行数")
    private final Integer affected;

    private AffectedResult(Integer affected) {
        this.affected = affected;
    }

    public static AffectedResult notAffected() {
        return new AffectedResult(0);
    }

    public static AffectedResult of(Integer affected) {
        return new AffectedResult(affected);
    }

    public Integer getAffected() {
        return affected;
    }
}
