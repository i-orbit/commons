package com.inmaytide.orbit.commons.domain.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author inmaytide
 * @since 2023/4/7
 */
@ApiModel("分页查询接口参数基类")
public class Pageable extends Query {

    @ApiModelProperty("当前页码")
    private Integer pageNumber;

    @ApiModelProperty("每页数据条数")
    private Integer pageSize;

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
