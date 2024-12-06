package com.inmaytide.orbit.commons.business;

import com.inmaytide.orbit.commons.domain.SystemUser;

import java.io.Serializable;
import java.util.Optional;

/**
 * @author inmaytide
 * @since 2023/11/24
 */
public interface SystemUserService {

    SystemUser get(Serializable id);

    Optional<SystemUser> findByUsername(String username);

}
