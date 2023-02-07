package org.example.utils;

import lombok.Data;
import org.example.enums.HTTPCode;

import java.io.Serializable;

@Data
public class JsonResult<T> implements Serializable {
    String message;
    Integer code;
    T data;

    public JsonResult() {

    }

    public JsonResult(Integer code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public static <T> JsonResult<T> ok() {
        return new JsonResult<>(HTTPCode.OK.getCode(), null, "ok");
    }

    public static <T> JsonResult<T> ok(T data) {
        return new JsonResult<T>(HTTPCode.OK.getCode(), data, "ok");
    }

}
