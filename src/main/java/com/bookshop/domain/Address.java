package com.bookshop.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable //jpa내장타입 - 어딘가 내장 될 수 있음
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    protected Address() {

    }

//값 타입은 변경 불가능하게 설계해야함
    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
