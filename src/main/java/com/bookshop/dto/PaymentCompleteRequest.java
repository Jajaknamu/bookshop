package com.bookshop.dto;

import lombok.Getter;
import lombok.Setter;

/*
* 포트원 결제 완료 시 프론트에서 전달하는 요청 DTO
* */
@Getter @Setter
public class PaymentCompleteRequest {
    private String paymentId; // PortOne 고유 결제 ID
}
