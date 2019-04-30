package com.mlnx.common.entity;


import io.swagger.annotations.ApiModelProperty;

public class ResponseData<T> extends Response {
    private static final long serialVersionUID = -8736246478845693990L;

    @ApiModelProperty(value = "承载数据")
    private T obj;

    public ResponseData() {
    }

    public ResponseData(T obj) {
        this.obj = obj;
    }


    public ResponseData(String rspCode, String rspMsg) {
        super(rspCode, rspMsg);
    }

    public ResponseData(String rspCode, String rspMsg, T obj) {
        super(rspCode, rspMsg);
        this.obj = obj;
    }

    public ResponseData(BaseExceptionMsg baseExceptionMsg, T obj) {
        super(baseExceptionMsg);
        this.obj = obj;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

    @Override
    public String toString() {
        return "ResponseData{" +
                "obj=" + obj +
                "} " + super.toString();
    }
}
