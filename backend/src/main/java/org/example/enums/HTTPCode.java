package org.example.enums;

public enum HTTPCode implements CodeInterface<Integer, String> {

    OK(200, "成功"),
    NOT_FOUND(404, "无法访问"),
    ERROR(400, "错误"),
    ;

    final Integer code;
    final String message;

    HTTPCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
