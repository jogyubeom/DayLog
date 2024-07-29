package com.daylog.domain.jwt.service;

import com.daylog.domain.jwt.JwtUtil;
import com.daylog.domain.user.dto.LoginRequestDto;
import com.daylog.domain.user.entity.User;
import com.daylog.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class JwtService {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public String login(LoginRequestDto loginDto) {
        String email = loginDto.getEmail();
        String password = loginDto.getPassword();
        User findUser = userRepository.findByEmail(email).orElse(null);
        if (findUser == null) {
            throw new UsernameNotFoundException("이메일이 존재하지 않습니다");
        }
        // 암호화된 password를 디코딩한 값과 입력한 패스워드 값이 다르면 null 반환
        if (encoder.matches(password, findUser.getPassword())) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }

        String accessToken = jwtUtil.generateAccessToken(findUser);
        return accessToken;
    }
}
