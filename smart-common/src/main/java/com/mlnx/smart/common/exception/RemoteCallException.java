package com.mlnx.smart.common.exception;

/**
 * Created by amanda.shan on 2019/3/29.
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
