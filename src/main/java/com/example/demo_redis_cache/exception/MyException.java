package com.example.demo_redis_cache.exception;

import lombok.Getter;

@Getter
public class MyException extends RuntimeException {
    private final String message;

    public MyException(final String message) {
        this.message = message;
    }

}
