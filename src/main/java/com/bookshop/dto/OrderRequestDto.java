package com.bookshop.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "주문 요청 DTO")
@Getter @Setter
public class OrderRequestDto {

    @Schema(description = "회원 ID", example = "1")
    private Long memberId;

    @Schema(description = "상품 ID", example = "101")
    private Long itemId;

    @Schema(description = "주문 수량", example = "2")
    private int count;
}
