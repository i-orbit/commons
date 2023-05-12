package com.inmaytide.orbit.commons.log;

public enum OperateResult {

    SUCCESS("success", "操作成功"),

    FAIL("failure", "操作失败");

    private final String value;

    private final String name;

    OperateResult(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
