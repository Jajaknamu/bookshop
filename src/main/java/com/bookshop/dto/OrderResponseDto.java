package com.bookshop.dto;

import com.bookshop.domain.Order;
import com.bookshop.domain.OrderStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OrderResponseDto {
    private Long orderId;
    private String memberName;
    private OrderStatus status;
    private LocalDateTime orderDate;
    private int totalPrice;
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
