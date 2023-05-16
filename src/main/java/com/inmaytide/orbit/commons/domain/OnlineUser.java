package com.inmaytide.orbit.commons.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inmaytide.orbit.commons.consts.Platforms;
import com.inmaytide.orbit.commons.utils.ApplicationContextHolder;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.Serializable;
import java.time.Instant;

/**
 * @author inmaytide
 * @since 2023/5/16
 */
public class OnlineUser implements Serializable {

    private Long id;

    private Platforms platform;

    private Instant onlineTime;

    private String ipAddress;

    private Instant lastActivityTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Platforms getPlatform() {
        return platform;
    }

    public void setPlatform(Platforms platform) {
        this.platform = platform;
    }

    public Instant getOnlineTime() {
        return onlineTime;
    }

    public void setOnlineTime(Instant onlineTime) {
        this.onlineTime = onlineTime;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Instant getLastActivityTime() {
        return lastActivityTime;
    }

    public void setLastActivityTime(Instant lastActivityTime) {
        this.lastActivityTime = lastActivityTime;
    }

    @Override
    public String toString() {
        try {
            return ApplicationContextHolder.getInstance().getBean(ObjectMapper.class).writeValueAsString(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static OnlineUser formJson(Object value) {
        if (value != null && !StringUtils.isBlank(value.toString())) {
            try {
                return ApplicationContextHolder.getInstance().getBean(ObjectMapper.class).readValue(value.toString(), OnlineUser.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            return null;
        }
    }
}
