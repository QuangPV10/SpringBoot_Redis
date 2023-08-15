package com.example.demo_redis_cache.exception;

public class InteralServerException extends MyException {

    public InteralServerException(String message, Long errorCode) {
        super(message, errorCode);
    }

    public InteralServerException(String message, Long errorCode, Object ... arguments) {
        super(message, errorCode, arguments);
    }
    
}
