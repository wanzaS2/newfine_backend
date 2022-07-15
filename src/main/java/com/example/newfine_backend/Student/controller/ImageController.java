package com.example.newfine_backend.Student.controller;

import com.example.newfine_backend.Student.dto.response.Result;
import com.example.newfine_backend.Student.service.ResponseService;
import com.example.newfine_backend.Student.service.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageController {

    private final S3Uploader s3Uploader;
    private final ResponseService responseService;

    @PostMapping("/upload")
    public Result upload(@RequestParam("image") MultipartFile multipartFile) throws IOException {
        return responseService.getSingleResult(s3Uploader.upload(multipartFile, "profile-image"));
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam("image")String  fileName) {
        s3Uploader.delete(fileName,"profile-image");
        return responseService.getSuccessResult();
    }
}
