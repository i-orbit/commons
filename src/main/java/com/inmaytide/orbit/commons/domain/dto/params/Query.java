package com.inmaytide.orbit.commons.domain.dto.params;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

/**
 * 列表查询接口参数基类
 *
 * @author inmaytide
 * @since 2023/4/7
 */
public class Query implements Serializable {

    @Parameter(description = "关键字搜索")
    private String queryName;

    public <T> LambdaQueryWrapper<T> toWrapper() {
        return new LambdaQueryWrapper<>();
    }

    public String getQueryName() {
        return queryName;
    }

    public void setQueryName(String queryName) {
        this.queryName = queryName;
    }
}
