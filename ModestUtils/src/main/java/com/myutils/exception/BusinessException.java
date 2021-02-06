package com.myutils.exception;

/**
 * 业务异常
 *
 * @author: wuyl
 * @date: 2019/5/16
 * @version: 0.0.1-SNAPSHOT
 */
public class BusinessException extends BaseException {
    public BusinessException(String msg) {
        super(msg);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }
}
