package com.inmaytide.orbit.commons.business.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.inmaytide.orbit.commons.business.BasicService;
import com.inmaytide.orbit.commons.domain.pattern.Entity;

/**
 * @author inmaytide
 * @since 2023/11/24
 */
public abstract class BasicServiceImpl<M extends BaseMapper<T>, T extends Entity> extends ServiceImpl<M, T> implements BasicService<T> {
}
