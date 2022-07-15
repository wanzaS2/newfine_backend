package com.example.newfine_backend.Student.dto;

import com.example.newfine_backend.Student.domain.Authority;
import com.example.newfine_backend.Student.domain.Student;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDto {
    private String phoneNumber;
    private String name;
    private String password;

    public Student toMember(PasswordEncoder passwordEncoder) {
        return Student.builder()
                .phoneNumber(phoneNumber)
                .name(name)
                .password(passwordEncoder.encode(password))
                .authority(Authority.ROLE_USER)
                .build();
    }
}
