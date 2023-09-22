package com.inmaytide.orbit.commons.domain.dto.result;

import com.github.pagehelper.PageInfo;
import com.inmaytide.orbit.commons.domain.pattern.Entity;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * @author inmaytide
 * @since 2023/4/7
 */
@Schema(title = "分页查询返回实体")
public class PageResult<T extends Entity> {

    @Schema(title = "当前页码")
    private Integer pageNumber;

    @Schema(title = "每页数据条数")
    private Integer pageSize;

    @Schema(title = "数据总条数")
    private Integer total;

    @Schema(title = "当前页数据列表")
    private List<T> elements;

    private PageResult() {

    }

    public static <T extends Entity> PageResult<T> with(PageInfo<T> pi) {
        PageResult<T> res = new PageResult<>();
        res.setPageNumber(pi.getPageNum());
        res.setPageSize(pi.getPageSize());
        res.setElements(pi.getList());
        res.setTotal(Long.valueOf(pi.getTotal()).intValue());
        return res;
    }

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

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<T> getElements() {
        return elements;
    }

    public void setElements(List<T> elements) {
        this.elements = elements;
    }
}
