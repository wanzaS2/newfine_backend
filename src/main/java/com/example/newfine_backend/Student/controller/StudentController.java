package com.example.newfine_backend.Student.controller;

import com.example.newfine_backend.Student.dto.StudentResponseDto;
import com.example.newfine_backend.Student.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class StudentController {
    private final StudentService studentService;

    @GetMapping("/me")
    public ResponseEntity<StudentResponseDto> getMyMemberInfo() {
        return ResponseEntity.ok(studentService.getMyInfo());
    }

    @GetMapping("/{phoneNumber}")
    public ResponseEntity<StudentResponseDto> getMemberInfo(@PathVariable String phoneNumber) {
        return ResponseEntity.ok(studentService.getMemberInfo(phoneNumber));
    }

//    /////// ㅁㄱ
//    @PostMapping("/myInfo")
//    public ResponseEntity<MemberResponseDto> getMyInfo(@RequestHeader("token") String token){
//        token.replace("Bearer ", "");
//        return ResponseEntity.ok(authService.findMemberByToken(token));
//    }
//
//
//    @PostMapping("/nickname")
//    public String setNickname(@RequestBody NickRequestDto nickRequestDto){
//        Optional<Member> member = memberRepository.findByPhoneNumber(nickRequestDto.getPhoneNumber());
////        authService.parseClaims1(token);
//
//
//        return nickRequestDto.getNickname();
//    }

    //    @PostMapping("/myInfo")
//    public ResponseEntity<MemberResponseDto> getMyInfo(@RequestHeader("authorization") String token){
//        token.replace("Bearer ", "");
//        return ResponseEntity.ok(authService.findMemberByToken(token));
//    }
}
