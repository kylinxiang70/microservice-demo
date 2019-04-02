package org.kylin.authcenter.entity;

public enum Operation {
    EQ("EQ");

    private String code;

    Operation(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
