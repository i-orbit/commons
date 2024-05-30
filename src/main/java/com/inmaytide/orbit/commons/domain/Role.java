package com.inmaytide.orbit.commons.domain;

import com.inmaytide.orbit.Version;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author inmaytide
 * @since 2024/5/30
 */
public class Role implements Serializable {

    @Serial
    private static final long serialVersionUID = Version.SERIAL_VERSION_UID;

    private Long id;

    private String code;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
