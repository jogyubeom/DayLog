package com.daylog.domain.user.dto;

import com.daylog.domain.user.entity.User;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CustomUserInfoDto  {
    private int id;

    private String email;

    private String name;

    private String password;

    private String coupleCode;

}
