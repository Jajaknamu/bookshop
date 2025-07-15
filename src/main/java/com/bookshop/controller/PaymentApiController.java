package com.bookshop.controller;

import com.bookshop.dto.PaymentCompleteRequest;
import com.bookshop.service.OrderService;
import com.bookshop.service.PaymentVerificationService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
public class PaymentApiController {
    private final PaymentVerificationService paymentVerificationService;
    private final OrderService orderService;

    @PostMapping("/complete")
    public ResponseEntity<?> completePayment(@RequestBody PaymentCompleteRequest request) {

        JsonNode paymentInfo = paymentVerificationService.verifyPayment(request.getPaymentId());

        int paidAmount = paymentInfo.get("amount").asInt();
        String status = paymentInfo.get("status").asText();

        if (!"paid".equals(status)) {
            return ResponseEntity.badRequest().body("결제 상태가 유효하지 않습니다: " + status);
        }

        JsonNode customData = paymentInfo.get("custom_data");
        long itemId = customData.get("itemId").asLong();
        long memberId = customData.get("memberId").asLong();
        int count = customData.get("count").asInt();
        System.out.println("customData: " + customData.toString());

        System.out.println("=== 결제 완료 요청 도착 ===");
        System.out.println("paymentId: " + request.getPaymentId());
        System.out.println("검증 결과: " + paymentInfo.toString());

        //주문 생성
        Long orderId = orderService.order(memberId, itemId, count);
        System.out.println("주문 생성 완료: orderId = " + orderId);

        return ResponseEntity.ok(Map.of(
                "status", "PAID",
                "amount",paidAmount,
                "orderId",orderId

        ));
    }
}
