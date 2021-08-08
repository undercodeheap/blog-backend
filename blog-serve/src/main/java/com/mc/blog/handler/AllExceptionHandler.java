package com.mc.blog.handler;

import com.mc.blog.vo.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

// 对加了Controller的方法进行拦截处理 AOP实现
@ControllerAdvice
public class AllExceptionHandler {

    // 进行异常处理，处理 Exception.class 的异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result doException(Exception ex){
        ex.printStackTrace();
        return Result.fail(-999, "系统异常");
    }
}
