package com.bookshop.controller;

import com.bookshop.domain.Order;
import com.bookshop.domain.Payment;
import com.bookshop.dto.OrderRequestDto;
import com.bookshop.dto.OrderResponseDto;
import com.bookshop.repository.OrderSearch;
import com.bookshop.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Tag(name = "Order API", description = "주문 ")
@RequestMapping("/api/orders")
public class OrderApiController {

    private final OrderService orderService;

    //주문 생성
    @Operation(summary = "주문 생성", description = "클라이언트가 주문 시 주문 생성")
    @ApiResponse(responseCode = "200", description = "주문 생성 성공")
    @PostMapping
    public ResponseEntity<Long> createOrder(@RequestBody OrderRequestDto dto) {
        Long orderId = orderService.order(dto.getMemberId(), dto.getItemId(), dto.getCount());
        return ResponseEntity.ok(orderId);
    }

    //주문 목록
    @Operation(summary = "주문 목록 조회", description = "전제 주문 목록을 조회")
    @ApiResponse(responseCode = "200", description = "주문 목록 조회 성공")
    @GetMapping
    public List<OrderResponseDto> listOrders(@ModelAttribute OrderSearch orderSearch) {
        List<Order> orders = orderService.findOrders(orderSearch);
        return orders.stream()
                .map(OrderResponseDto::new)
                .collect(Collectors.toList());
    }

    //주문 상세(optional)
    @Operation(summary = "주문 상세", description = "주문 정보 상세 확인")
    @ApiResponse(responseCode = "200", description = "주문 상세 조회")
    @GetMapping("/{id}")
    public OrderResponseDto getOrder(@PathVariable Long id) {
        Order order = orderService.findOrders(new OrderSearch()).stream()
                .filter(o -> o.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(("주문 없음")));
        return new OrderResponseDto(order);
    }

    //주문 취소
    @Operation(summary = "주문 취소", description = "주문 ID로 취소")
    @ApiResponse(responseCode = "200", description = "주문취소")
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<String> cancelOrder(@PathVariable Long id) {
        orderService.cancelOrder(id);
        return ResponseEntity.ok("주문이 취소되었습니다");
    }
}
