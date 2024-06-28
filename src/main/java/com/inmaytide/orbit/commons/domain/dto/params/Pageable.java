package com.inmaytide.orbit.commons.domain.dto.params;

import com.inmaytide.orbit.Version;
import com.inmaytide.orbit.commons.domain.pattern.Entity;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;

/**
 * 分页查询接口参数基类
 *
 * @author inmaytide
 * @since 2023/4/7
 */
public abstract class Pageable<T extends Entity> extends Query<T> {

    @Serial
    private static final long serialVersionUID = Version.SERIAL_VERSION_UID;

    @Schema(title = "当前页码")
    private Integer pageNumber = 1;

    @Schema(title = "每页数据条数")
    private Integer pageSize = 10;

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
