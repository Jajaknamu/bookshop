package com.bookshop.member.controller;

import lombok.Getter;

@Getter //응답 객체 getter 자동 생성
public class LoginResponse {

    private final String token; //JWT 또는 에러 메시지

    public LoginResponse(String token) {
        this.token = token; //생성자로 값 주입
    }
}