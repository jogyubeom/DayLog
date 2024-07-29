package com.daylog.domain.user.service;

import com.daylog.domain.user.dto.PostSignUpUserReq;
import com.daylog.domain.user.entity.User;
import com.daylog.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;

    //회원가입
    public void signUp(PostSignUpUserReq postSignUpUserReq) {
        if(userRepository.existsByEmail(postSignUpUserReq.getEmail())) {
            throw new IllegalStateException("이미 사용중인 이메일입니다");
        }

        if(userRepository.existsByTel(postSignUpUserReq.getTel())) {
            throw new IllegalStateException("이미 사용중인 전화번호입니다");
        }

        User user = postSignUpUserReq.toEntity("image/Path", generateCoupleCode());

        userRepository.save(user);
    }

    private String generateCoupleCode() {
        return "testcode";
    }

}
