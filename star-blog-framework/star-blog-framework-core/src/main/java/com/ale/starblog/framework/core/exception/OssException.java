package com.ale.starblog.framework.core.exception;

import com.ale.starblog.framework.common.exception.VenusException;

/**
 * OSS对象存储异常
 *
 * @author Ale
 * @version 1.0.0 2025/9/28 14:55
 */
public class OssException extends VenusException {

    public OssException(int code, String message) {
        super(code, message);
    }

    public OssException(int code, String message, Object... args) {
        super(code, message, args);
    }

    public OssException(String message) {
        super(message);
    }

    public OssException(String message, Object... args) {
        super(message, args);
    }

    public OssException(int code, String message, Throwable cause) {
        super(code, message, cause);
    }

    public OssException(int code, String message, Throwable cause, Object... args) {
        super(code, message, cause, args);
    }

    public OssException(String message, Throwable cause) {
        super(message, cause);
    }

    public OssException(String message, Throwable cause, Object... args) {
        super(message, cause, args);
    }

}
