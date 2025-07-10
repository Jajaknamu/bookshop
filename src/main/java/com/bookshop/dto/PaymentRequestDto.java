package com.bookshop.dto;

import lombok.Getter;
import lombok.Setter;

//프론트에서 결제 후 서버로 전송하는 요청 데이터를 담는 dto
@Getter @Setter
public class PaymentRequestDto {

    private String imp_uid;       // 아임포트 고유 결제번호
    private String merchant_uid;  // 주문번호 (고유해야 함)
    private Long memberId;        // 주문자 ID
    private Long itemId;          // 주문 상품 ID
    private int count;            // 주문 수량

}
