package com.mlnx.smart.service.exception;

/**
 * Created by amanda.shan on 2019/7/1.
 */
public class SmartServiceException extends RuntimeException {

    public SmartServiceException() {
    }

    public SmartServiceException(String message) {
        super(message);
    }

    public SmartServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public SmartServiceException(Throwable cause) {
        super(cause);
    }

    public SmartServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
