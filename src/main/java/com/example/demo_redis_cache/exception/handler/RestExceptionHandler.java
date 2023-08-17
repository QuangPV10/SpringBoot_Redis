package com.example.demo_redis_cache.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo_redis_cache.exception.BadRequestException;
import com.example.demo_redis_cache.exception.InternalServerException;
import com.example.demo_redis_cache.exception.MyException;
import com.example.demo_redis_cache.exception.NotFoundException;
import com.example.demo_redis_cache.exception.ValidationException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class RestExceptionHandler {

    @ResponseBody
    @ExceptionHandler(MyException.class)
    public ResponseEntity<Object> handleException(MyException myException) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(myException.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<Object> handleException(InternalServerException interalServerException) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(interalServerException.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleException(NotFoundException notFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFoundException.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleException(BadRequestException badRequestException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badRequestException.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Object> handleException(HttpRequestMethodNotSupportedException exception) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(exception.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleException(ValidationException validationException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationException.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleException(MethodArgumentNotValidException methodArgumentNotValidException) {
        Map<String, String> errors = new HashMap<>();
        methodArgumentNotValidException.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
    
}
