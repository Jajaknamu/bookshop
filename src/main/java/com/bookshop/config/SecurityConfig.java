package com.bookshop.config;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity // 스프링 시큐리티 활성화
@Configuration //설정 클래스라고 스프링에 알려주는 어노테이션
public class SecurityConfig {

    //비밀번호 암호화를 위한 메서드
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/", "/members/new", "/api/items").permitAll()
                        .anyRequest().authenticated()
                );
        //csrf는 항상 켜두어야함. 공부적 허용으로 꺼두기
        http
                .csrf((csrf) -> csrf
                        .ignoringRequestMatchers("/member/new")
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
        return http.build();
    }

}
