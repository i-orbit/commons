package com.inmaytide.orbit.commons.business;

import com.inmaytide.orbit.commons.domain.SystemUser;

import java.io.Serializable;

/**
 * @author inmaytide
 * @since 2023/11/24
 */
public interface SystemUserService {

    SystemUser get(Serializable id);

}
