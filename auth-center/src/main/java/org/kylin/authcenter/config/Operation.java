package org.kylin.authcenter.config;

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
