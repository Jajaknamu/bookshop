package com.bookshop.config;

import com.bookshop.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseCookie;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity // 스프링 시큐리티 활성화
@Configuration //설정 클래스라고 스프링에 알려주는 어노테이션
@RequiredArgsConstructor //final 필드로 생성자 자동 생성
public class SecurityConfig {

    private static final String ACCESS_TOKEN_COOKIE = "accessToken";


    //비밀번호 암호화를 위한 메서드
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(); //비밀번호 암호화 객체 생성
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception{

        http
                .authorizeHttpRequests((auth) -> auth
                        //정적 리소스 허용
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                        // 화면 접근 허용
                        .requestMatchers(HttpMethod.GET, "/", "/members/new", "/loginPage").permitAll()
                        // [수정] hasAuthority("ROLE_USER") → hasAnyAuthority : ADMIN도 주문 목록 접근 허용
                        .requestMatchers(HttpMethod.GET,"/orders").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                        //item 디테일 보기
                        .requestMatchers(HttpMethod.GET,"/items/*").hasAuthority("ROLE_USER")
                        //회원가입/로그인 api 허용
                        .requestMatchers(HttpMethod.POST, "/api/members","/api/login", "/logout").permitAll()
                        //상품 조회 허용(메인페이지용)
                        .requestMatchers(HttpMethod.GET, "/api/items").permitAll()
                        .anyRequest().authenticated() //나머지는 인증 필요
                );
        //csrf는 항상 켜두어야함. 공부적 허용으로 꺼두기. JWT는 Stateless라 CSRF 비활성화
        http
                .csrf(csrf -> csrf.disable())
                        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        //커스텀 로그인 설정
        http
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable());

        // 로그아웃 jwt 쿠키 삭제하도록 설정
        http
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                        .deleteCookies(ACCESS_TOKEN_COOKIE)
                        .addLogoutHandler((request, response, authentication) -> {
                            ResponseCookie deleteCookie = ResponseCookie.from(ACCESS_TOKEN_COOKIE, "")
                                    .httpOnly(true)
                                    .secure(false)   // HTTPS면 true
                                    .path("/")
                                    .sameSite("Lax")
                                    .maxAge(0)       // ✅ 즉시 만료(삭제)
                                    .build();
                            response.addHeader(HttpHeaders.SET_COOKIE, deleteCookie.toString());

                            SecurityContextHolder.clearContext();
                        })
                );

        // [수정] 인증 실패 시(미로그인) /loginPage로 리다이렉트하도록 EntryPoint 설정
        http
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.sendRedirect("/loginPage");
                        })
                );

        //jwt 필터 추가 -> UsernamePasswordAuthenticationFilter 앞에 실행됨
        http
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); //JWT 필터 등록
        return http.build();
    }

}
