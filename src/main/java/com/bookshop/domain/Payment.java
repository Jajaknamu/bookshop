package com.bookshop.domain;

import jakarta.persistence.*;
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

    private LocalDateTime canceledAt; // 결제 취소 일시

    // Order 연관관계 추가
    @OneToOne(mappedBy = "payment") // Order에 정의된 필드명
    private Order order;

    public void setOrder(Order order) {
        this.order = order;
        //양방향 관계 자동 설정
        if (order.getPayment() != this) {
            order.setPayment(this);
        }
    }
}
