package com.bookshop.dto;

import com.bookshop.domain.Address;
import com.bookshop.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Schema(description = "회원등록 및 응답 DTO")
public class MemberDto {

    @Schema(description = "회원 이름")
    private String name;

    @Schema(description = "비밀번호")
    private String password;

    @Schema(description = "도시")
    private String city;

    @Schema(description = "거리")
    private String street;

    @Schema(description = "우편번호")
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
