package com.example.newfine_backend.Student.dto;

import com.example.newfine_backend.Student.domain.Student;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponseDto {
    private String phoneNumber;
    private String nickname;
    private String photoURL;

    public static StudentResponseDto of(Student student) {
        return new StudentResponseDto(student.getPhoneNumber(), student.getNickname(), student.getPhotoURL());
    }
}
