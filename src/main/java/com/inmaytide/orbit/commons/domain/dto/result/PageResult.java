package com.inmaytide.orbit.commons.domain.dto.result;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.inmaytide.orbit.Version;
import com.inmaytide.orbit.commons.domain.pattern.Entity;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * @author inmaytide
 * @since 2023/4/7
 */
@Schema(title = "分页查询返回实体")
public class PageResult<T extends Entity> implements Serializable {

    @Serial
    private static final long serialVersionUID = Version.SERIAL_VERSION_UID;

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

    public static <T extends Entity> PageResultBuilder<T> builder() {
        return new PageResultBuilder<>();
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

    public static class PageResultBuilder<T extends Entity> {

        private Integer pageNumber;

        private Integer pageSize;

        private Integer total;

        private List<T> elements;

        private IPage<T> page;

        public PageResultBuilder<T> pageNumber(Integer pageNumber) {
            this.pageNumber = Objects.requireNonNull(pageNumber);
            return this;
        }

        public PageResultBuilder<T> pageNumber(Long pageNumber) {
            this.pageNumber = Objects.requireNonNull(pageNumber).intValue();
            return this;
        }

        public PageResultBuilder<T> pageSize(Integer pageSize) {
            this.pageSize = Objects.requireNonNull(pageSize);
            return this;
        }

        public PageResultBuilder<T> pageSize(Long pageSize) {
            this.pageSize = Objects.requireNonNull(pageSize).intValue();
            return this;
        }

        public PageResultBuilder<T> total(Integer total) {
            this.total = Objects.requireNonNull(total);
            return this;
        }

        public PageResultBuilder<T> total(Long total) {
            this.total = Objects.requireNonNull(total).intValue();
            return this;
        }

        public PageResultBuilder<T> elements(List<T> elements) {
            this.elements = Objects.requireNonNull(elements);
            return this;
        }

        public PageResultBuilder<T> page(IPage<T> page) {
            this.page = page;
            return this;
        }

        public PageResult<T> build() {
            PageResult<T> res = new PageResult<>();
            if (page != null) {
                res.setPageNumber(Long.valueOf(page.getCurrent()).intValue());
                res.setPageSize(Long.valueOf(page.getSize()).intValue());
                res.setElements(page.getRecords());
                res.setTotal(Long.valueOf(page.getTotal()).intValue());
            }

            res.setPageNumber(pageNumber == null ? res.getPageNumber() : pageNumber);
            res.setPageSize(pageSize == null ? res.getPageSize() : pageSize);
            res.setTotal(total == null ? res.getTotal() : total);
            res.setElements(elements == null ? res.getElements() : elements);
            return res;
        }
    }

}
