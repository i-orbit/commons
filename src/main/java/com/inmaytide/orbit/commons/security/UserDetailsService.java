package com.inmaytide.orbit.commons.security;

import com.inmaytide.orbit.commons.domain.GlobalUser;

import java.io.Serializable;

/**
 * @author inmaytide
 * @since 2023/4/7
 */
public interface UserDetailsService {

    GlobalUser loadUserById(Serializable id);

}
