package com.inmaytide.orbit.commons.provider;

import com.inmaytide.orbit.commons.domain.GlobalUser;

import java.io.Serializable;

/**
 * @author inmaytide
 * @since 2023/4/7
 */
public interface UserDetailsProvider {

    GlobalUser get(Serializable id);

}
