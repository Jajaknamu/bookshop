package com.bookshop.controller;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.EnableMBeanExport;

@Getter @Setter
public class MemberForm {

    @NotEmpty(message = "회원이름은 필수 기재")
    private String name;
    private String password;

    private String city;
    private String street;
    private String zipcode;
}
