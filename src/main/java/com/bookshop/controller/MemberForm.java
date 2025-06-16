package com.bookshop.controller;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.EnableMBeanExport;

@Getter @Setter
@Schema(description = "회원강비 form 데이터")
public class MemberForm {

    @NotEmpty(message = "회원이름은 필수 기재")
    @Schema(description = "회원이름")
    private String name;

    @Schema(description = "비밀번호")
    private String password;

    @Schema(description = "도시")
    private String city;

    @Schema(description = "거리")
    private String street;

    @Schema(description = "우편번호")
    private String zipcode;
}
