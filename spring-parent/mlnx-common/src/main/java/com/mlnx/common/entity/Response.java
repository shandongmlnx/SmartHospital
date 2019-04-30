package com.mlnx.common.entity;


import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

public class Response implements Serializable {

    private static final long serialVersionUID = -6319875363658293056L;
    /**
     * 返回信息码
     */
    @ApiModelProperty(value = "状态码", required = true)
    private String code = "0000";

    /**
     * 返回信息内容
     */
    @ApiModelProperty(value = "返回消息", required = true)
    private String msg = "操作成功";

    public Response() {
    }

    public Response(String code) {
        this.code = code;
        this.msg = "";
    }

    public Response(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Response(BaseExceptionMsg baseExceptionMsg) {
        this.code = baseExceptionMsg.getCode();
        this.msg = baseExceptionMsg.getMsg();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSucess() {
        return "0000".equals(code);
    }

    @Override
    public String toString() {
        return "Response{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
