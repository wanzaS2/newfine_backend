package com.example.newfine_backend.Student.dto;

import lombok.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInDto {
    private String phoneNumber;
    private String password;

//    @Builder
//    public SignInDto(String phonenumber, String password){
//        this.phonenumber=phonenumber;
//        this.password=password;
//    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(phoneNumber, password);
    }
}
