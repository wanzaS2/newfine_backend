package com.example.newfine_backend.Student.service;

import com.example.newfine_backend.Student.dto.PhoneNumberDto;
import com.example.newfine_backend.Student.exception.CustomException;
import com.example.newfine_backend.Student.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import net.nurigo.java_sdk.api.Message;
import org.springframework.beans.factory.annotation.Value;
import org.json.simple.JSONObject;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.springframework.stereotype.Service;

import java.util.HashMap;

import static com.example.newfine_backend.Student.exception.ErrorCode.DUPLICATE_MEMBER;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final StudentRepository studentRepository;

    @Value("${coolsms.nf.apikey}")
    private String apiKey;

    @Value("${coolsms.nf.apisecret}")
    private String apiSecret;

    @Value("${coolsms.nf.fromnumber}")
    private String fromNumber;

    public String sendMessage(PhoneNumberDto phoneNumberDto, String randomNumber) {

        // 전화번호 중복 확인
        if (studentRepository.existsByPhoneNumber(phoneNumberDto.getPhoneNumber())) {
            throw new CustomException(DUPLICATE_MEMBER);
        }

        Message coolsms = new Message(apiKey, apiSecret);

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("to", phoneNumberDto.getPhoneNumber());
        params.put("from", fromNumber);
        params.put("type", "SMS");
        params.put("text", "[newfine] \n인증번호 "+randomNumber+" 를 입력하세요.");
        params.put("app_version", "test app 1.2"); // application name and version

        try {
            JSONObject obj = (JSONObject) coolsms.send(params);
            System.out.println(obj.toString());
        } catch (CoolsmsException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCode());
        }

        return randomNumber;
    }
}
