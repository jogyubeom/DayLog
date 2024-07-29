package com.daylog.domain.user.dto;

import com.daylog.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

//Getter, Setter, toString, eqauls
// 생성자 주입(RequiredArgsConstructor + final) 자동으로 지원
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostSignUpUserReq {
    private String email;
    private String password;
    private String name;
    private String tel;
    private String nickname;
    private String birthDate;

    public User toEntity(String imagePath, String coupleCode) {
        return User.builder()
                .email(email)
                .password(password)
                .name(name)
                .tel(tel)
                .nickname(nickname)
                .imagePath(imagePath)
                .coupleCode(coupleCode)
                .birthDate(birthDate)
                .build();
    }
}
