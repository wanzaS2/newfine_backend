package com.example.newfine_backend.Student.service;

import com.example.newfine_backend.Student.domain.Student;
import com.example.newfine_backend.Student.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final StudentRepository studentRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // UserDetails 와 Authentication 의 패스워드를 비교하고 검증하는 로직을 처리

        return studentRepository.findByPhoneNumber(username)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException(username + " -> 데이터베이스에서 찾을 수 없습니다."));
    }

    // DB 에 User 값이 존재한다면 UserDetails 객체로 만들어서 리턴
    private UserDetails createUserDetails(Student student) {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(student.getAuthority().toString());

        return new User(
                String.valueOf(student.getId()),
                student.getPassword(),
                Collections.singleton(grantedAuthority)
        );
    }
}
