package com.bookshop.controller;

import com.bookshop.dto.OrderResponseDto;
import com.bookshop.repository.OrderSearch;
import com.bookshop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
//@RequestMapping("/api/admin/orders")
@RequiredArgsConstructor
public class AdminOrderController {

    private final OrderService orderService;

    //모든 주문 조회
    @GetMapping("/api/admin/orders")
    public List<OrderResponseDto> listAll() {
        return orderService.findOrders(new OrderSearch()).stream()
                .map(OrderResponseDto::new)
                .toList();
    }

    //RESTful 하게 설계하기.
    @GetMapping("/api/admin/orders2")
    public ResponseEntity<List<OrderResponseDto>> listAll2() {
        List<OrderResponseDto> orders = orderService.findOrders(new OrderSearch()).stream()
                .map(OrderResponseDto::new)
                .toList();
        return ResponseEntity.ok(orders);
    }
}
