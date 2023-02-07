package org.example.utils;

public enum Valid {
    TRUE(1),
    FALSE(0);

    public Integer getCode() {
        return code;
    }

    final Integer code;

    Valid(Integer code) {
        this.code = code;
    }

    public static final String column = "valid";

}
