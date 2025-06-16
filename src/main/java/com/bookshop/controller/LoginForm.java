package com.bookshop.controller;

import lombok.Getter;
import lombok.Setter;

//controller에 두는 이유 : 화면/폼과 직접 연결되는 Form 객체, 로그인 화면(form)에서 입력한 값을 담는 용도

@Getter @Setter
public class LoginForm {
    private String name;
    private String password;

}
