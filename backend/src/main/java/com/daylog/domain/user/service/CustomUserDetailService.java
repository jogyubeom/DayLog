package com.daylog.domain.user.service;

import com.daylog.domain.user.dto.CustomUserDetails;
import com.daylog.domain.user.dto.CustomUserInfoDto;
import com.daylog.domain.user.entity.User;
import com.daylog.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ModelMapper mapper;

    //UserDetailService 인터페이페이스 오버라이딩
    //JwtAuthFilter에서 Jwt 유효성을 검증 한 후 Jwt의 Claims에서 추출한 id를 데이터베이스에서 조회한 후
    //확인된다면 Spring Security에서 내부적으로 사용되는 Auth 객체를 만들 때 필요한 UserDetails 객체를 반환한다.
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User findUser = userRepository.findById(Long.parseLong(userId)).orElseThrow(() -> new UsernameNotFoundException("해당 유저가 존재하지 않습니다."));

        CustomUserInfoDto dto = findUser.toCustomUserInfoDto();

        return new CustomUserDetails(dto);
    }
}
