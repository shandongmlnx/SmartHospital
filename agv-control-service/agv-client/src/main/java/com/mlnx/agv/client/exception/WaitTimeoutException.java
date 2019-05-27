package com.mlnx.agv.client.exception;

/**
 * Created by amanda.shan on 2018/12/19.
 */
public class WaitTimeoutException extends RuntimeException {

    public WaitTimeoutException(String message) {
        super(message);
    }
}
