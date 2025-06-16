package com.bookshop.dto;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberUpdateDto {
    private String name;
    private String password;
    private String city;
    private String street;
    private String zipcode;
}
