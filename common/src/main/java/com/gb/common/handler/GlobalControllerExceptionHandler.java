package com.gb.common.handler;

import com.gb.common.util.Log;
import com.gb.common.model.vo.ResponseModel;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.ResponseEntity.*;

/**
 * 全局Controller异常捕捉类
 * 可以用于对{@link Valid}、{@link RequestParam}、{@link RequestBody} 等注解参数不符合要求时抛出的异常进行处理。
 * @author gblau
 * @date 2017-04-22
 */
@ControllerAdvice
public class GlobalControllerExceptionHandler {
    public static final Logger logger = Log.get(GlobalControllerExceptionHandler.class);

    /**
     * controller出错。
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseModel> handleUncaughtedException(Exception e) {
        Log.error(logger, "全局异常处理捕捉到一个错误。", e);
        //ResponseEntity和@RequestBody的不同在于，他可以定义自己的返回状态码
        return status(INTERNAL_SERVER_ERROR).body(ResponseModel.internerServerError().build());
    }

    /**
     * 参数缺失。
     * @param e
     * @return
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ResponseModel> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        Log.info(logger, e.getLocalizedMessage());
        return badRequest().body(ResponseModel.badRequest().message(""));
    }
}
