package com.example.newfine_backend.Student.service;

import com.example.newfine_backend.Student.dto.response.Result;
import com.example.newfine_backend.Student.dto.response.SingleResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResponseService {
    public <T> SingleResult<T> getSingleResult(T data) {
        SingleResult<T> result = new SingleResult<>();
        result.setData(data);
        setSuccessResult(result);
        return result;
    }

    public Result getSuccessResult() {
        Result result = new Result();
        setSuccessResult(result);
        return result;
    }

    private <T> void setSuccessResult(Result result) {
        result.setSuccess(true);
        result.setCode(0);
        result.setMessage("success");
    }

}
