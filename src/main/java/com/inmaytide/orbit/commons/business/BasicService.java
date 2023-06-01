package com.inmaytide.orbit.commons.business;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.inmaytide.exception.web.ObjectNotFoundException;
import com.inmaytide.orbit.commons.consts.Is;
import com.inmaytide.orbit.commons.domain.dto.params.Pageable;
import com.inmaytide.orbit.commons.domain.dto.result.AffectedResult;
import com.inmaytide.orbit.commons.domain.dto.result.PageResult;
import com.inmaytide.orbit.commons.domain.pattern.Entity;
import com.inmaytide.orbit.commons.domain.pattern.TombstoneEntity;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author inmaytide
 * @since 2023/4/7
 */
public interface BasicService<T extends Entity> {

    BaseMapper<T> getMapper();

    Class<T> getEntityClass();

    /**
     * 更新操作(增/删/改)后执行的一些通用操作
     * 比如: 清除相关缓存
     */
    default void updated() {

    }

    default T create(T entity) {
        getMapper().insert(entity);
        updated();
        return get(entity.getId()).orElseThrow(() -> new ObjectNotFoundException(String.valueOf(entity.getId())));
    }

    default AffectedResult deleteByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return AffectedResult.notAffected();
        }
        int affected = 0;
        if (getEntityClass().isAssignableFrom(TombstoneEntity.class)) {
            try {
                Constructor<T> constructor = ReflectionUtils.accessibleConstructor(getEntityClass());
                T seed = constructor.newInstance();
                ((TombstoneEntity) seed).setDeleted(Is.Y);
                affected = getMapper().update(seed, new LambdaUpdateWrapper<T>().in(T::getId, ids));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            affected = getMapper().deleteBatchIds(ids);
        }
        updated();
        return AffectedResult.of(affected);
    }

    default T update(T entity) {
        getMapper().updateById(entity);
        updated();
        return get(entity.getId()).orElseThrow(() -> new ObjectNotFoundException(String.valueOf(entity.getId())));
    }

    default PageResult<T> pagination(Pageable params) {
        PageInfo<T> pi = PageHelper.startPage(params.getPageNumber(), params.getPageSize())
                .doSelectPageInfo(() -> getMapper().selectList(params.toWrapper()));
        setExtraAttributes(pi.getList());
        return PageResult.of(pi);
    }

    default Optional<T> get(Long id) {
        T t = getMapper().selectById(id);
        if (t == null) {
            return Optional.empty();
        }
        setExtraAttributes(Collections.singleton(t));
        return Optional.of(t);
    }

    default void setExtraAttributes(Collection<T> entities) {

    }

}
