package com.example.newfine_backend.Student.dto;

import com.example.newfine_backend.Student.domain.Authority;
import com.example.newfine_backend.Student.domain.Student;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StudentRequestDto {

    // 로그인..? 회원가입?
    private String phoneNumber;
    private String password;
//    private String nickname;

    public Student toMember(PasswordEncoder passwordEncoder) {
        return Student.builder()
                .phoneNumber(phoneNumber)
                .password(passwordEncoder.encode(password))
//                .nickname(nickname)
                .authority(Authority.ROLE_USER)
                .build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(phoneNumber, password);
    }
}
