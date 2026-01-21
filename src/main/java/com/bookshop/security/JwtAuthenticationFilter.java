package com.bookshop.security;

import com.bookshop.member.domain.Member;
import com.bookshop.member.service.MemberService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;//JWT 생성/검증 도구
    private final MemberService memberService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // 요청 헤더에서 토큰 추출
        String token = resolveToken(request);

        // 토큰이 있고 유효하면
        if (token != null && jwtTokenProvider.validateToken(token)) {
            // 토큰에서 사용자 이름 추출
            String username = jwtTokenProvider.getUsername(token);
            //db에서 사용자 조회
            Member member = memberService.findByName(username);

            if (member != null) {
                List<SimpleGrantedAuthority> authorities =
                        List.of(new SimpleGrantedAuthority("ROLE_" + member.getRole())); // 권한 리스트 생성
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(member.getName(), null, authorities); // 인증 객체 생성
                SecurityContextHolder.getContext().setAuthentication(authentication); // SecurityContext에 저장
            }
        }
        filterChain.doFilter(request, response); //다음 필터로 이동

    }

    // Authorization 헤더에서 Bearer 토큰 추출
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization"); //헤더 읽이

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) { //Bearer형식 확인
            return bearerToken.substring(7); // "Bearer " 이후 문자열 반환
        }
        if (request.getCookies() != null) { //쿠키가 있으면
            for (var cookie : request.getCookies()) { // 쿠키 반복
                if ("accessToken".equals(cookie.getName())) { //이름이 accessToken이면
                    return cookie.getValue(); //쿠키의 토큰 반환
                }
            }
        }

        return null; //토큰 없으면 null
    }
}