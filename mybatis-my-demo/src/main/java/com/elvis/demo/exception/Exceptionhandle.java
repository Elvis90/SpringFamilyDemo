package com.elvis.demo.exception;

import com.elvis.demo.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Elvis
 * @create 2019-06-27 9:42
 * controller抛出异常的全局处理
 */
@RestControllerAdvice
@Slf4j
public class Exceptionhandle {
    @ExceptionHandler(Exception.class)
    ResponseEntity exception(Exception e) {
        e.printStackTrace();
        log.error("全局异常捕获"+e.getMessage());
        return new ResponseEntity<R>(R.error(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
