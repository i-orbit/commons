package com.inmaytide.orbit.commons.business;

import com.inmaytide.orbit.commons.domain.Oauth2Token;

/**
 * @author inmaytide
 * @since 2025/1/3
 */
public interface RefreshTokenService {

    Oauth2Token refreshToken(String refreshToken);

}
