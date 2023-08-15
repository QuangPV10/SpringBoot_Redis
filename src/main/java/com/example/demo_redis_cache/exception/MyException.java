package com.example.demo_redis_cache.exception;

import lombok.Getter;

@Getter
public class MyException extends RuntimeException {
    private final transient String message;
    private final transient Long errorCode;
    private final transient Object[] arguments;

    public MyException(final String message, final Long errorCode) {
        this.errorCode = errorCode;
        this.message = message;
        this.arguments = new Object[0];
    }

    public MyException(final String message, final Long errorCode, final Object ... arguments) {
        this.errorCode = errorCode;
        this.message = message;
        this.arguments = arguments;
    }

}
