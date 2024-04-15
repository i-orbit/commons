package com.inmaytide.orbit.commons.domain.dto.params;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.inmaytide.orbit.commons.domain.pattern.Entity;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;
import java.io.Serializable;

/**
 * 列表查询接口参数基类
 *
 * @author inmaytide
 * @since 2023/4/7
 */
public abstract class Query<T extends Entity> implements Serializable {

    @Serial
    private static final long serialVersionUID = 5168025452213712156L;

    @Schema(title = "关键字搜索")
    private String queryName;

    public abstract LambdaQueryWrapper<T> toWrapper();

    public String getQueryName() {
        return queryName;
    }

    public void setQueryName(String queryName) {
        this.queryName = queryName;
    }
}
