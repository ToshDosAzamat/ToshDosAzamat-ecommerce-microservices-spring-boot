package com.example.orderservice.exception;

import com.example.orderservice.dto.ExceptionMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class NotFoundExceptionController {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> runexception(RuntimeException exception){
        ExceptionMessage exceptionMessage = ExceptionMessage.builder()
                .date(new Date())
                .message(exception.getMessage())
                .httpStatus(HttpStatus.NOT_FOUND)
                .build();
        return new ResponseEntity<>(exceptionMessage, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> total_exception(Exception exception){
        ExceptionMessage exceptionMessage = ExceptionMessage.builder()
                .date(new Date())
                .message(exception.getMessage())
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
        return new ResponseEntity<>(exceptionMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
