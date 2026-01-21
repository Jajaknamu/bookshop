package com.bookshop.security;

import com.bookshop.member.domain.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final SecretKey secretKey;
    private final long expirationMs;

    //application.yml에서 jwt.secret 값 가져옴
    public JwtTokenProvider(@Value("${jwt.secret}") String secret,
                            @Value("${jwt.expiration-ms}")long expirationMs) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationMs = expirationMs;
    }

    //JWT 생성 -> 로그인 성공 시 호출해서 토큰을 만들어 줌
    public String generateToken(Member member) {
        Date now = new Date();
        Date expiry  = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                .setSubject(member.getName()) //토큰 주인 식별자(이름)
                .claim("role", member.getRole()) //권한 정보
                .setIssuedAt(now) //발급 시간
                .setExpiration(expiry) //만료 시간
                .signWith(secretKey, SignatureAlgorithm.HS256) //서명 알고리즘 + 비밀키
                .compact(); //문자열 토큰 생성
    }
    //JWT 검증 -> 서명이 맞고 만료되지 않았는지 확인
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //토큰에서 이름(subject)을 꺼내는 메서드
    public String getUsername(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

}
