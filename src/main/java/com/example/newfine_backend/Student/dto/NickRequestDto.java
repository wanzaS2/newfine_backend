package com.example.newfine_backend.Student.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NickRequestDto {
    private String phoneNumber;
    private String nickname;
}
