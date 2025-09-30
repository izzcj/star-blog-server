package com.ale.starblog.framework.security.exception;

import com.ale.starblog.framework.common.exception.ExceptionCode;
import com.ale.starblog.framework.common.exception.VenusException;

/**
 * 权限不足异常
 *
 * @author Ale
 * @version 1.0.0
 * @since 2024/6/27
 **/
public class InsufficientPermissionException extends VenusException {

    public InsufficientPermissionException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }

    public InsufficientPermissionException(int code, String message) {
        super(code, message);
    }

    public InsufficientPermissionException(int code, String message, Object... args) {
        super(code, message, args);
    }

    public InsufficientPermissionException(String message) {
        super(message);
    }

    public InsufficientPermissionException(String message, Object... args) {
        super(message, args);
    }

    public InsufficientPermissionException(int code, String message, Throwable cause) {
        super(code, message, cause);
    }

    public InsufficientPermissionException(int code, String message, Throwable cause, Object... args) {
        super(code, message, cause, args);
    }

    public InsufficientPermissionException(String message, Throwable cause) {
        super(message, cause);
    }

    public InsufficientPermissionException(String message, Throwable cause, Object... args) {
        super(message, cause, args);
    }
}
