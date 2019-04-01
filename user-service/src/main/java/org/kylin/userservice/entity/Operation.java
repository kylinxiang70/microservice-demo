package org.kylin.userservice.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Operation {
    EQ("EQ");

    private String code;

    private Operation(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
