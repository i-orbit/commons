package com.inmaytide.orbit.commons.domain;

import com.inmaytide.orbit.commons.utils.ApplicationContextHolder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.List;

/**
 * @author inmaytide
 * @since 2023/4/12
 */
public class OrbitClientDetails implements Serializable {

    public static final String ORBIT_CLIENT_ID = "orbit";

    private volatile static OrbitClientDetails INSTANCE;

    private String clientId;

    private String clientSecret;

    private List<String> authorizedGrantTypes;

    private Integer accessTokenValiditySeconds;

    private Integer refreshTokenValiditySeconds;

    private List<String> scopes;

    private String introspectionUri;

    private OrbitClientDetails() {
    }

    public static OrbitClientDetails getInstance() {
        if (null == INSTANCE) {
            synchronized (OrbitClientDetails.class) {
                if (null == INSTANCE) {
                    if (INSTANCE == null) {
                        INSTANCE = ApplicationContextHolder.getInstance()
                                .getBinder()
                                .bind("orbit.security", OrbitClientDetails.class)
                                .orElseThrow(() -> new UnsupportedOperationException("There is no valid security client details configuration in the spring context"));

                        if (StringUtils.isBlank(INSTANCE.clientId)
                                || StringUtils.isBlank(INSTANCE.clientSecret)
                                || StringUtils.isBlank(INSTANCE.introspectionUri)
                                || INSTANCE.accessTokenValiditySeconds == null
                                || INSTANCE.accessTokenValiditySeconds <= 0
                                || INSTANCE.refreshTokenValiditySeconds == null
                                || INSTANCE.refreshTokenValiditySeconds <= 0
                                || CollectionUtils.isEmpty(INSTANCE.authorizedGrantTypes)) {
                            throw new UnsupportedOperationException("There is no valid security client details configuration in the spring context");
                        }

                    }
                }
            }

        }
        return INSTANCE;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public List<String> getAuthorizedGrantTypes() {
        return authorizedGrantTypes;
    }

    public void setAuthorizedGrantTypes(List<String> authorizedGrantTypes) {
        this.authorizedGrantTypes = authorizedGrantTypes;
    }

    public Integer getAccessTokenValiditySeconds() {
        return accessTokenValiditySeconds;
    }

    public void setAccessTokenValiditySeconds(Integer accessTokenValiditySeconds) {
        this.accessTokenValiditySeconds = accessTokenValiditySeconds;
    }

    public Integer getRefreshTokenValiditySeconds() {
        return refreshTokenValiditySeconds;
    }

    public void setRefreshTokenValiditySeconds(Integer refreshTokenValiditySeconds) {
        this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
    }

    public List<String> getScopes() {
        return scopes;
    }

    public void setScopes(List<String> scopes) {
        this.scopes = scopes;
    }

    public String getIntrospectionUri() {
        return introspectionUri;
    }

    public void setIntrospectionUri(String introspectionUri) {
        this.introspectionUri = introspectionUri;
    }
}
