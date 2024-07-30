package com.daylog.user.dto;

import com.daylog.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class UserRequestDto {

    @Getter
    public static class SignUp {
        private String email;
        private String password;
        private String name;
        private String tel;
        private Date birthDate;

        public User toEntity() {
            return User.builder()
                    .email(email)
                    .hashedPassword(password)
                    .name(name)
                    .tel(tel)
                    .nickname(name)
                    .profileImagePath("기본 이미지 경로")
                    .coupleCode("커플코드 로직 생성해야함")
                    .birthDate(birthDate)
                    .build();
        }
    }
}
