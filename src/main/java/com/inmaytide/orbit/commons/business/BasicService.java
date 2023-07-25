package com.inmaytide.orbit.commons.business;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.inmaytide.exception.web.ObjectNotFoundException;
import com.inmaytide.orbit.commons.domain.dto.params.Pageable;
import com.inmaytide.orbit.commons.domain.dto.result.AffectedResult;
import com.inmaytide.orbit.commons.domain.dto.result.PageResult;
import com.inmaytide.orbit.commons.domain.pattern.Entity;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author inmaytide
 * @since 2023/4/7
 */
public interface BasicService<T extends Entity> extends IService<T> {

    /**
     * 更新操作(增/删/改)后执行的一些通用操作
     * 比如: 清除相关缓存
     */
    default void updated() {

    }

    default T create(T entity) {
        getBaseMapper().insert(entity);
        updated();
        return get(entity.getId()).orElseThrow(() -> new ObjectNotFoundException(String.valueOf(entity.getId())));
    }

    default AffectedResult deleteByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return AffectedResult.notAffected();
        }
        return AffectedResult.of(getBaseMapper().deleteBatchIds(ids));
    }

    default T update(T entity) {
        getBaseMapper().updateById(entity);
        updated();
        return get(entity.getId()).orElseThrow(() -> new ObjectNotFoundException(String.valueOf(entity.getId())));
    }

    default PageResult<T> pagination(Pageable<T> params) {
        try (Page<T> p = PageHelper.startPage(params.getPageNumber(), params.getPageSize())) {
            PageInfo<T> pi = p.doSelectPageInfo(() -> getBaseMapper().selectList(params.toWrapper()));
            setExtraAttributes(pi.getList());
            return PageResult.of(pi);
        }
    }

    default Optional<T> get(Long id) {
        T t = getBaseMapper().selectById(id);
        if (t == null) {
            return Optional.empty();
        }
        setExtraAttributes(Collections.singleton(t));
        return Optional.of(t);
    }

    default void setExtraAttributes(Collection<T> entities) {

    }

}
