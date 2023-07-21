package com.inmaytide.orbit.commons.domain.dto.params;

import com.inmaytide.orbit.commons.domain.pattern.Entity;
import io.swagger.v3.oas.annotations.Parameter;

import java.io.Serial;

/**
 * 分页查询接口参数基类
 *
 * @author inmaytide
 * @since 2023/4/7
 */
public class Pageable<T extends Entity> extends Query<T> {

    @Serial
    private static final long serialVersionUID = -4333214352924071775L;

    @Parameter(description = "当前页码")
    private Integer pageNumber;

    @Parameter(description = "每页数据条数")
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
