package com.inmaytide.orbit.commons.domain.params;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author inmaytide
 * @since 2023/4/7
 */
@ApiModel("查询接口参数基类")
public class Query implements Serializable {

    @ApiModelProperty("关键字搜索")
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
