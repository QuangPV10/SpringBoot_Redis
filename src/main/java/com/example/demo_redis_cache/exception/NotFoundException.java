package com.example.demo_redis_cache.exception;

public class NotFoundException extends MyException {

    public NotFoundException(String message, Long errorCode) {
        super(message, errorCode);
    }

}
