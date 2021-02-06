package com.myutils.exception;

/**
 * 基础异常
 *
 * @author: wuyl
 * @date: 2019/5/16
 * @version: 0.0.1-SNAPSHOT
 */
public class BaseException extends RuntimeException {
    public BaseException() {
    }

    public BaseException(String message, int status) {
        super(message);
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
