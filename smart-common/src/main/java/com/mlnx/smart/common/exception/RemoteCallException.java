package com.mlnx.smart.common.exception;

/**
 * Created by amanda.shan on 2019/7/1.
 */
public class RemoteCallException extends RuntimeException {

    public RemoteCallException() {
    }

    public RemoteCallException(String message) {
        super(message);
    }

    public RemoteCallException(String message, Throwable cause) {
        super(message, cause);
    }
}
