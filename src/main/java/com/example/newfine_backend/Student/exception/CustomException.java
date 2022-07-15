package com.example.newfine_backend.Student.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException { // Unchecked Exception
    private final ErrorCode errorCode;
}
