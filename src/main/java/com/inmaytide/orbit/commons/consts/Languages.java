package com.inmaytide.orbit.commons.consts;

/**
 * 系统支持的语言
 *
 * @author inmaytide
 * @since 2023/4/4
 */
public enum Languages {

    SIMPLIFIED_CHINESE("简体中文");

    private final String description;

    Languages(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
