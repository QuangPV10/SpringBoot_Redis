package com.example.demo_redis_cache.exception;

public class BadRequestException extends MyException {

    public BadRequestException(String message, Long errorCode) {
        super(message, errorCode);
    }

    
    public BadRequestException(String message, Long errorCode, Object ... arguments) {
        super(message, errorCode, arguments);
    }
    
}
