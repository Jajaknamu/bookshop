package com.bookshop.dto;

import com.bookshop.domain.Order;
import com.bookshop.domain.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;

@Schema(description = "주문 응답 DTO")
@Getter
public class OrderResponseDto {

    @Schema(description = "주문 ID", example = "1")
    private Long orderId;

    @Schema(description = "주문한 회원 이름", example = "test1")
    private String memberName;

    @Schema(description = "주문 상태", example = "ORDER")
    private OrderStatus status;

    @Schema(description = "주문 날짜", example = "2025-06-13T15:30:00")
    private LocalDateTime orderDate;

    @Schema(description = "총 주문 금액", example = "50000")
    private int totalPrice;

    @Schema(description = "주문한 아이템 수량", example = "2")
    private int itemCount; //주문한 책수량

    //엔티티를 dto로 변환
    public OrderResponseDto(Order order) {
        this.orderId = order.getId();
        this.memberName = order.getMember().getName();
        this.status = order.getStatus();
        this.orderDate = order.getOrderDate();
        this.totalPrice = order.getTotalPrice();
        this.itemCount = order.getOrderItems().get(0).getCount();
    }
}
