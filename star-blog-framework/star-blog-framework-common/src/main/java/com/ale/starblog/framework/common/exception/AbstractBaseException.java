package com.ale.starblog.framework.common.exception;

import cn.hutool.core.text.StrFormatter;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * 异常基类
 *
 * @author Ale
 * @version 1.0.0
 * @since 2024/6/24
 **/
@Getter
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractBaseException extends RuntimeException {

    /**
     * 异常响应码
     */
    private final int code;

    public AbstractBaseException() {
        super(ExceptionCode.DEFAULT_SERVICE_ERROR.getMsg());
        this.code = ExceptionCode.DEFAULT_ERROR.getCode();
    }

    public AbstractBaseException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMsg());
        this.code = exceptionCode.getCode();
    }

    public AbstractBaseException(int code, String message) {
        super(message);
        this.code = code;
    }

    public AbstractBaseException(int code, String message, Object... args) {
        super(StrFormatter.format(message, args));
        this.code = code;
    }

    public AbstractBaseException(String message) {
        super(message);
        this.code = ExceptionCode.DEFAULT_ERROR.getCode();
    }

    public AbstractBaseException(String message, Object... args) {
        super(StrFormatter.format(message, args));
        this.code = ExceptionCode.DEFAULT_ERROR.getCode();
    }

    public AbstractBaseException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public AbstractBaseException(int code, String message, Throwable cause, Object... args) {
        super(StrFormatter.format(message, args), cause);
        this.code = code;
    }

    public AbstractBaseException(String message, Throwable cause) {
        super(message, cause);
        this.code = ExceptionCode.DEFAULT_ERROR.getCode();
    }

    public AbstractBaseException(String message, Throwable cause, Object... args) {
        super(StrFormatter.format(message, args), cause);
        this.code = ExceptionCode.DEFAULT_ERROR.getCode();
    }
}
