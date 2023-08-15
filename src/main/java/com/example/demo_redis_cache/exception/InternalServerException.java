package com.example.demo_redis_cache.exception;

public class InternalServerException extends MyException {

    public InternalServerException(String message, Long errorCode) {
        super(message, errorCode);
    }
    
}
