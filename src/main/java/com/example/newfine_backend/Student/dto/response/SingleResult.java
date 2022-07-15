package com.example.newfine_backend.Student.dto.response;

import lombok.Data;

@Data
public class SingleResult<T> extends Result {
    private T data;
}
