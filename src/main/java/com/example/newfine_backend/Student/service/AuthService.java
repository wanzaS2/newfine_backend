package com.example.newfine_backend.Student.service;

import com.example.newfine_backend.Student.domain.Student;
import com.example.newfine_backend.Student.dto.*;
import com.example.newfine_backend.Student.exception.CustomException;
import com.example.newfine_backend.Student.jwt.RefreshToken;
import com.example.newfine_backend.Student.jwt.TokenProvider;
import com.example.newfine_backend.Student.repository.StudentRepository;
import com.example.newfine_backend.Student.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.newfine_backend.Student.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
//@AllArgsConstructor
public class AuthService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

//    @Value("${jwt.secret}")
//    private final String secretKey;
//
//    private Key secretK;

//    public void initK() {
//        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
//        this.secretK = Keys.hmacShaKeyFor(keyBytes);
//    }

    @Transactional
    public StudentResponseDto signup(SignUpDto signUpDto) {
        if (studentRepository.existsByPhoneNumber(signUpDto.getPhoneNumber())) {
            throw new CustomException(DUPLICATE_MEMBER);
        }

        Student student = signUpDto.toMember(passwordEncoder);
//      ㅊㅂ redisTemplate.opsForZSet().add("ranking",member.getNickname(), member.getPoint());
        return StudentResponseDto.of(studentRepository.save(student));
    }

    // 로그인 예외처리**
    @Transactional
    public TokenDto login(SignInDto signInDto) {
        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성 (인증 정보 객체 UsernamePasswordAuthenticationToken 생성)
        UsernamePasswordAuthenticationToken authenticationToken = signInDto.toAuthentication();

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        //    인증 완료된 authentication 에는 Member ID 저장됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성 (Access Token + Refresh Token)
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 4. RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);

        // 5. 토큰 발급
        return tokenDto;
    }

//    @Transactional
//    public TokenDto reissue(TokenRequestDto tokenRequestDto) {  // 토큰 재발급
//        // 1. Refresh Token 만료 여부 검증
//        if (!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
//            throw new CustomException(INVALID_REFRESH_TOKEN);
//        }
//
//        // 2. Access Token 복호화 -> Member ID 가져오기
//        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());
//
//        // 3. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴
//        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
//                .orElseThrow(() -> new CustomException(REFRESH_TOKEN_NOT_FOUND));
//
//        // 4. 클라이언트의 Refresh Token 일치하는지 검사
//        if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
//            throw new CustomException(MISMATCH_REFRESH_TOKEN);
//        }
//
//        // 5. (일치할 경우) 새로운 토큰 생성
//        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);
//
//        // 6. 저장소 정보 업데이트 (이전의 Refresh Token 을 사용할 수 없도록)
//        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
//        refreshTokenRepository.save(newRefreshToken);
//
//        // 토큰 발급
//        return tokenDto;
//    }

    @Transactional
    public TokenDto reissue(TokenRequestDto tokenRequestDto) {  // 토큰 재발급

        // 1. Refresh Token 만료 여부 검증
        if (!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new CustomException(INVALID_REFRESH_TOKEN);
        }

        // 2. Access Token 복호화 -> Member ID 가져오기
        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        // 3. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new CustomException(REFRESH_TOKEN_NOT_FOUND));

        // 4. 클라이언트의 Refresh Token 일치하는지 검사
        if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
            throw new CustomException(MISMATCH_REFRESH_TOKEN);
        }

        // 5. (일치할 경우) 새로운 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 6. 저장소 정보 업데이트 (이전의 Refresh Token 을 사용할 수 없도록)
        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        // 토큰 발급
        return tokenDto;
    }
}
