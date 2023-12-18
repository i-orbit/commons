package com.inmaytide.orbit.commons.business.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.inmaytide.orbit.commons.business.BasicService;
import com.inmaytide.orbit.commons.domain.pattern.Entity;

/**
 * @author inmaytide
 * @since 2023/11/24
 */
public abstract class BasicServiceImpl<T extends Entity> extends ServiceImpl<BaseMapper<T>, T> implements BasicService<T> {
}
