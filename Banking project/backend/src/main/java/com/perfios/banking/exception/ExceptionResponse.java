package com.perfios.banking.exception;

import lombok.Data;

@Data
public class ExceptionResponse {
    int errorCode;
    String message;
    String path;
}
