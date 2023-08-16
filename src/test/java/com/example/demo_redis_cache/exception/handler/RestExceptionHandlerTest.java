package com.example.demo_redis_cache.exception.handler;

import com.example.demo_redis_cache.exception.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
class RestExceptionHandlerTest {

    @InjectMocks
    private RestExceptionHandler restExceptionHandler;

    @Test
    void testHandleException_MyException() {
        String expectedMessage = "This is an error message";
        MyException myException = new MyException(expectedMessage);

        ResponseEntity<Object> responseEntity = restExceptionHandler.handleException(myException);

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        Assertions.assertEquals(expectedMessage, responseEntity.getBody());
    }

    @Test
    void testHandleException_InternalServerException() {
        String expectedMessage = "This is an error message";

        ResponseEntity<Object> responseEntity = restExceptionHandler.handleException(new InternalServerException(expectedMessage));

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        Assertions.assertEquals(expectedMessage, responseEntity.getBody());
    }

    @Test
    void testHandleException_NotFoundException() {
        String expectedMessage = "This is an error message";

        ResponseEntity<Object> responseEntity = restExceptionHandler.handleException(new NotFoundException(expectedMessage));

        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        Assertions.assertEquals(expectedMessage, responseEntity.getBody());
    }

    @Test
    void testHandleException_BadRequestException() {
        String expectedMessage = "This is an error message";

        ResponseEntity<Object> responseEntity = restExceptionHandler.handleException(new BadRequestException(expectedMessage));

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        Assertions.assertEquals(expectedMessage, responseEntity.getBody());
    }

    @Test
    void testHandleException_HttpRequestMethodNotSupportedException() {
        String expectedMessage = "This is an error message";

        ResponseEntity<Object> responseEntity = restExceptionHandler.handleException(new HttpRequestMethodNotSupportedException(expectedMessage));

        Assertions.assertEquals(HttpStatus.METHOD_NOT_ALLOWED, responseEntity.getStatusCode());

    }

    @Test
    void testHandleException_ValidationException() {
        String expectedMessage = "This is an error message";

        ResponseEntity<Object> responseEntity = restExceptionHandler.handleException(new ValidationException(expectedMessage));

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        Assertions.assertEquals(expectedMessage, responseEntity.getBody());
    }

    @Test
    void testHandleException_MethodArgumentNotValidException() {
        MethodArgumentNotValidException mockException = Mockito.mock(MethodArgumentNotValidException.class);
        BindingResult mockBindingResult = Mockito.mock(BindingResult.class);
        FieldError fieldError = new FieldError("objectName", "fieldName", "Field error message");
        Mockito.when(mockBindingResult.getAllErrors()).thenReturn(List.of(fieldError));
        Mockito.when(mockException.getBindingResult()).thenReturn(mockBindingResult);

        ResponseEntity<Object> responseEntity = restExceptionHandler.handleException(mockException);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        Map<String, String> expectedErrors = new HashMap<>();
        expectedErrors.put("fieldName", "Field error message");
        Assertions.assertEquals(expectedErrors, responseEntity.getBody());
    }

}

