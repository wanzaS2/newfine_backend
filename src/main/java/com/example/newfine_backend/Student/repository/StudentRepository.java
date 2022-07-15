package com.example.newfine_backend.Student.repository;

import com.example.newfine_backend.Student.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByPhoneNumber(String phoneNumber); // 전화번호가 아이디
    boolean existsByPhoneNumber(String phoneNumber);    // 중복 가입 방지용

    Optional<Student> findByNickname(String nickname);
    boolean existsByNickname(String nickname);
}
