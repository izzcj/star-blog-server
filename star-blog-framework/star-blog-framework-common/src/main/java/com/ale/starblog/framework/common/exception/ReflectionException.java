package com.ale.starblog.framework.common.exception;

/**
 * 反射异常
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/3/4
 */
public class ReflectionException extends AbstractBaseException {


    public ReflectionException(int code, String message) {
        super(code, message);
    }

    public ReflectionException(int code, String message, Object... args) {
        super(code, message, args);
    }

    public ReflectionException(String message) {
        super(message);
    }

    public ReflectionException(String message, Object... args) {
        super(message, args);
    }

    public ReflectionException(int code, String message, Throwable cause) {
        super(code, message, cause);
    }

    public ReflectionException(int code, String message, Throwable cause, Object... args) {
        super(code, message, cause, args);
    }

    public ReflectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReflectionException(String message, Throwable cause, Object... args) {
        super(message, cause, args);
    }
}
