package com.bookshop.controller;

import com.bookshop.domain.Order;
import com.bookshop.dto.OrderRequestDto;
import com.bookshop.dto.OrderResponseDto;
import com.bookshop.repository.OrderSearch;
import com.bookshop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderApiController {

    private final OrderService orderService;

    //주문 생성
    @PostMapping
    public ResponseEntity<Long> createOrder(@RequestBody OrderRequestDto dto) {
        Long orderId = orderService.order(dto.getMemberId(), dto.getItemId(), dto.getCount());
        return ResponseEntity.ok(orderId);
    }

    //주문 목록
    @GetMapping
    public List<OrderResponseDto> listOrders(@ModelAttribute OrderSearch orderSearch) {
        List<Order> orders = orderService.findOrders(orderSearch);
        return orders.stream()
                .map(OrderResponseDto::new)
                .collect(Collectors.toList());
    }

    //주문 상세(optional)
    @GetMapping("/{id}")
    public OrderResponseDto getOrder(@PathVariable Long id) {
        Order order = orderService.findOrders(new OrderSearch()).stream()
                .filter(o -> o.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(("주문 없음")));
        return new OrderResponseDto(order);
    }

    //주문 취소
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<String> cancelOrder(@PathVariable Long id) {
        orderService.cancelOrder(id);
        return ResponseEntity.ok("주문이 취소되었습니다");
    }
}
