package com.dandinglong.controller;

import com.dandinglong.exception.MyRunntimeException;
import com.dandinglong.util.JsonResult;
import com.dandinglong.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@ControllerAdvice
public class GlobalExceptionHandler {
    private Logger logger= LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler({Exception.class})
    @ResponseBody
    public JsonResult exceptionDeal(HttpServletRequest request, HttpServletResponse response,Exception e){
        logger.error("请求错误",e);
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json;charset=UTF-8");
        return ResultUtil.fail(e);
    }
    @ExceptionHandler({MyRunntimeException.class})
    @ResponseBody
    public JsonResult myRunntimeExceptionDeal(HttpServletRequest request, HttpServletResponse response,MyRunntimeException e){
        logger.error("请求错误"+e.getMessage());
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json;charset=UTF-8");
        return ResultUtil.fail(e);
    }
}
