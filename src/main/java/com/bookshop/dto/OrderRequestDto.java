package com.bookshop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderRequestDto {
    private Long memberId;
    private Long itemId;
    private int count;
}
