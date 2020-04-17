package com.arthur.shardingsphere.sharding_demo.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Slf4j
@ControllerAdvice
@ResponseBody
public class WebExceptionHandler {
    /**
     *  拦截Exception类的异常
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultEntity exceptionHandler(MethodArgumentNotValidException e){
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        StringBuffer tips = new StringBuffer();
        for (ObjectError err : errors) {
            tips.append(err.getDefaultMessage()+" | ");
        }
        log.info("前端请求参数校验失败："+tips.toString());
        return new ResultEntity().validateFailure(tips.toString());
    }
}
