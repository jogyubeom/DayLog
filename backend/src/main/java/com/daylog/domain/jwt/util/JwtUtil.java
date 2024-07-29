package com.daylog.domain.jwt;

import com.daylog.domain.user.dto.PostSignUpUserReq;
import com.daylog.domain.user.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.ZonedDateTime;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil {
    private final Key key;  // JWT 서명에 사용될 비밀 키
    private final long accessTokenExpireTime;  // Access Token의 만료 시간 (초 단위)

    /**
     * JwtUtil 생성자
     *
     * @param secretKey JWT 서명에 사용될 비밀 키 (Base64 Encoding)
     * @param accessTokenExpireTime Access Token의 만료 시간
     */

    public JwtUtil(
            @Value("${jwt.secret}") String secretKey,
            @Value("${jwt.expiration_time}") long accessTokenExpireTime)
    {
        // Base64로 인코딩된 비밀 키를 디코딩하여 Key 객체로 변환
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenExpireTime = accessTokenExpireTime;
    }

    /**
     * 사용자 정보를 기반으로 Access Token을 생성합니다.
     *
     * @param user 사용자 정보
     * @return 생성된 Access Token 문자열
     */

    public String generateAccessToken(User user) {
        return generateToken(user, accessTokenExpireTime);
    }

    /**
     * 사용자 정보를 기반으로 JWT 토큰을 생성합니다.
     *
     * @param user 사용자 정보
     * @param expiredTime 토큰의 만료 시간 (초 단위)
     * @return 생성된 JWT 토큰 문자열
     */

    private String generateToken(User user, long expiredTime) {
        Claims claims = Jwts.claims();  // JWT의 클레임(속성) 설정
        claims.put("id", user.getId());  // 사용자 ID를 클레임으로 추가
        claims.put("email", user.getEmail());  // 사용자 이메일을 클레임으로 추가

        ZonedDateTime now = ZonedDateTime.now();  // 현재 시각
        ZonedDateTime tokenValidUntil = now.plusSeconds(expiredTime);  // 토큰 만료 시각

        return Jwts.builder()
                .setClaims(claims)  // 클레임 설정
                .setIssuedAt(Date.from(now.toInstant()))  // 발급 일시 설정
                .setExpiration(Date.from(tokenValidUntil.toInstant()))  // 만료 일시 설정
                .compact();  // JWT 토큰 생성
    }

    /**
     * JWT 토큰에서 사용자 ID를 추출합니다.
     *
     * @param token JWT 토큰 문자열
     * @return 추출된 사용자 ID
     */

    public int getUserId(String token) {
        return parseClaims(token).get("id", Integer.class);  // 클레임에서 사용자 ID 추출
    }

    /**
     * JWT 토큰에서 클레임을 추출합니다.
     *
     * @param accessToken JWT 토큰 문자열
     * @return JWT 클레임
     */

    public Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            // 토큰이 만료되었으나 클레임을 반환
            return e.getClaims();
        }
    }

    /**
     * JWT 토큰의 유효성을 검증합니다.
     *
     * @param token JWT 토큰 문자열
     * @return 토큰이 유효하면 true, 그렇지 않으면 false
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);  // 토큰 파싱 및 검증
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            // 서명 오류 또는 형식 오류
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            // 만료된 토큰
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            // 지원하지 않는 토큰 형식
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            // 비어 있는 JWT 클레임 문자열
            log.info("JWT claims string is empty.", e);
        }
        return false;  // 유효하지 않은 경우 false 반환
    }
}
