package com.example.newfine_backend.Student.jwt;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


// ****************** Token 만료 시 자동 삭제 위해 추후 Redis 로 바꾸기
// ****************** RDB로 할 경우 생성/수정 시간 컬럼을 추가하여 배치 작업으로 만료된 토큰들을 삭제해주어야 함!!
@Getter
@NoArgsConstructor
@Table(name = "refresh_token")
@Entity
public class RefreshToken {

    @Id
    @Column(name = "rt_key")
    private String key; // Member ID

    @Column(name = "rt_value")
    private String value;   // Refresh Token String

    @Builder
    public RefreshToken(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public RefreshToken updateValue(String token) {
        this.value = token;
        return this;
    }
}
