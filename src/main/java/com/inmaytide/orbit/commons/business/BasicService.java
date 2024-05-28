package com.inmaytide.orbit.commons.business;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.inmaytide.exception.web.ObjectNotFoundException;
import com.inmaytide.orbit.commons.domain.dto.params.Pageable;
import com.inmaytide.orbit.commons.domain.dto.result.AffectedResult;
import com.inmaytide.orbit.commons.domain.dto.result.PageResult;
import com.inmaytide.orbit.commons.domain.pattern.Entity;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author inmaytide
 * @since 2023/4/7
 */
public interface BasicService<T extends Entity> extends IService<T> {

    default T create(T entity) {
        getBaseMapper().insert(entity);
        changed();
        return get(entity.getId()).orElseThrow(() -> new ObjectNotFoundException(String.valueOf(entity.getId())));
    }

    default AffectedResult deleteByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return AffectedResult.NOT_AFFECTED;
        }
        int affected = getBaseMapper().deleteBatchIds(ids);
        changed();
        return AffectedResult.withAffected(affected);
    }

    default T update(T entity) {
        getBaseMapper().updateById(entity);
        changed();
        return get(entity.getId()).orElseThrow(() -> new ObjectNotFoundException(String.valueOf(entity.getId())));
    }

    default PageResult<T> pagination(Pageable<T> params) {
        try (Page<T> p = PageHelper.startPage(params.getPageNumber(), params.getPageSize())) {
            PageInfo<T> pi = p.doSelectPageInfo(() -> getBaseMapper().selectList(params.toWrapper()));
            setExtraAttributes(pi.getList());
            return PageResult.with(pi);
        }
    }

    default List<T> findByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return List.of();
        }
        List<T> list = getBaseMapper().selectBatchIds(ids);
        setExtraAttributes(list);
        return list;
    }

    default Optional<T> get(Long id) {
        T t = getBaseMapper().selectById(id);
        if (t == null) {
            return Optional.empty();
        }
        setExtraAttributes(Collections.singleton(t));
        return Optional.of(t);
    }

    /**
     * 查询数据时设置一些非持久化用于展示的字段信息
     */
    default void setExtraAttributes(Collection<T> entities) {

    }

    /**
     * 查询指定数据对象的某个字段值 <br/>
     * 比如: 用于通过用户唯一标识获取对应的用户姓名
     *
     * @param ids         需要查询的数据对象唯一标识列表
     * @param fieldGetter 需获取的字段获取器
     */
    default Map<Long, String> findFieldValueByIds(List<Long> ids, SFunction<T, String> fieldGetter) {
        if (ids == null || ids.isEmpty()) {
            return Map.of();
        }
        LambdaQueryWrapper<T> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(fieldGetter, T::getId);
        wrapper.in(T::getId, ids);
        return getBaseMapper().selectList(wrapper).stream().collect(Collectors.toMap(T::getId, fieldGetter));
    }

    /**
     * 更新操作(增/删/改)后执行的一些通用操作
     * 比如: 清除相关缓存
     */
    default void changed() {

    }

}
