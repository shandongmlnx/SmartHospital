package com.mlnx.smart.user.controller;

import com.mlnx.common.entity.Response;
import com.mlnx.common.utils.MyLog;
import com.mlnx.smart.common.enums.ResponseEnum;
import com.mlnx.smart.service.exception.SmartServiceException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by amanda.shan on 2019/7/1.
 */
@ControllerAdvice
@ResponseBody
public class WebExceptionHandle {

    private static MyLog logger = MyLog.getLog(WebExceptionHandle.class);

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(SmartServiceException.class)
    public Response handleHttpUserException(SmartServiceException e) {

        logger.error(e, "用户模块当前请求方法");

        return new Response(ResponseEnum.USER_FAIL.getCode(), e.getMessage());
    }


}
