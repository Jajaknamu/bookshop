package com.bookshop.dto;

import com.bookshop.domain.Address;
import com.bookshop.domain.Member;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberDto {
    private String name;
    private String password;
    private String city;
    private String street;
    private String zipcode;

    //entity -> dto 변환용
    public static MemberDto from(Member member) {
        MemberDto dto = new MemberDto();
        dto.setName(member.getName());
        dto.setPassword(member.getPassword());
        dto.setCity(member.getAddress().getCity());
        dto.setStreet(member.getAddress().getStreet());
        dto.setZipcode(member.getAddress().getZipcode());
        return dto;
    }

    //dto -> entity 변환용
    public Member toEntity() {
        Member member = new Member();
        member.setName(this.name);
        member.setPassword(this.password);
        member.setAddress(new Address(this.city, this.street, this.zipcode));
        return member;
    }


}
