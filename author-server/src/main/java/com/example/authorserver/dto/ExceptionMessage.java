package com.example.authorserver.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
@Builder
public class ExceptionMessage {
    private Date date;
    private String message;
    private HttpStatus httpStatus;
}
