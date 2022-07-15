package com.example.newfine_backend.Student.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name="member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Student extends Timestamped {

    @Id
    @Column(name = "student_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column
    private String phoneNumber;

    @Column
    private String name;

    @JsonIgnore
    @Column
    private String password;

    @Column
    private String nickname;

    @Column
    private String photoURL;

    @Column
    @ColumnDefault("0")
    private int point;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @Column
    private LocalDateTime signupDate;

    @PrePersist
    public void signupDate() {
        this.signupDate = LocalDateTime.now();
    }

    @Builder
    public Student(String phoneNumber, String name, String password, String nickname, Authority authority, String photoURL, int point) {
        this.phoneNumber=phoneNumber;
        this.name=name;
        this.password = password;
        this.authority = authority;
        this.nickname= nickname;
        this.photoURL=photoURL;
        this.point=point;
    }
}
