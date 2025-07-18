package com.bookshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentVerificationResult {

    private String impUid;         // 아임포트 결제 고유번호
    private String merchantUid;    // 주문번호
    private int amount;            // 결제 금액
    private String status;         // 결제 상태
    private Long memberId;         // 주문자
    private Long itemId;           // 상품 ID
    private int count;             // 주문 수량
}
