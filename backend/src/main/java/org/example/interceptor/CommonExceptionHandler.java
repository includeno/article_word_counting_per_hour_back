package org.example.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class CommonExceptionHandler {
    /**
     * 对运行时异常进行统一异常管理方法
     * @param e
     * @return
     */
    @ExceptionHandler(BindException.class) // FlyException类继承于RuntimeException
    public ResponseEntity<Map<String, Object>> handlerException(BindException e) {

        Map<String, Object> result = new HashMap<>(3);
        result.put("message", e.getMessage());
        result.put("code", 400);
        result.put("data", null);
        return ResponseEntity.status(400).body(result);
    }
}
