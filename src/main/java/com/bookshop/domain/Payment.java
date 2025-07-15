package com.bookshop.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Payment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String impUid;        // 아임포트 결제 고유번호
    private String merchantUid;   // 주문번호
    private int amount;           // 결제 금액
    private String status;        // paid, failed 등

    private Long itemId;
    private Long memberId;
    private int count;

    private LocalDateTime paidAt; // 결제 일시
}
