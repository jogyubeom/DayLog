package com.daylog.domain.user.repository;

import com.daylog.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String username); //이메일 중복 여부
    boolean existsByTel(String tel); // 전화번호 중복 여부
    Optional<User> findByEmail(String email); //이메일로 User 찾기
}
