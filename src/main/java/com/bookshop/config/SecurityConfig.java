package com.bookshop.config;

import com.bookshop.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity // 스프링 시큐리티 활성화
@Configuration //설정 클래스라고 스프링에 알려주는 어노테이션
@RequiredArgsConstructor //final 필드로 생성자 자동 생성
public class SecurityConfig {

    //JWT 검증 필터 주입
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    //비밀번호 암호화를 위한 메서드
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(); //비밀번호 암호화 객체 생성
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception{

        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/", "/members/new", "/api/items", "/api/login").permitAll()
                        .anyRequest().authenticated() //나머지는 인증 필요
                );
        //csrf는 항상 켜두어야함. 공부적 허용으로 꺼두기. JWT는 Stateless라 CSRF 비활성화
        http
                .csrf((csrf) -> csrf
                        .ignoringRequestMatchers("/member/new", "/api/login")
                );
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) //세션 저장 안함
                );
        //커스텀 로그인 설정
        http
                .formLogin((auth) -> auth
                        .loginPage("/loginPage")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/login?error=true")
                        .usernameParameter("name")
                        .passwordParameter("password")
                        .permitAll()
                );
        http
                .sessionManagement((auth) -> auth
                        .sessionFixation().changeSessionId()
                );

        //jwt 필터 추가 -> UsernamePasswordAuthenticationFilter 앞에 실행됨
        http
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); //JWT 필터 등록
        return http.build();
    }

}
