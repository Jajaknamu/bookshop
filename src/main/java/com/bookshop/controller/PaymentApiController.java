package com.bookshop.controller;

import com.bookshop.dto.PaymentCompleteRequest;
import com.bookshop.service.OrderService;
import com.bookshop.service.PaymentVerificationService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
public class PaymentApiController {
    private final PaymentVerificationService paymentVerificationService;
    private final OrderService orderService;

    @PostMapping("/complete")
    public ResponseEntity<?> completePayment(@RequestBody Map<String, Object> payload) {
        try {
            //프론트에서 전달한 데이터 추출
            String paymentId = (String) payload.get("paymentId");
            Integer count = Integer.parseInt(payload.get("count").toString());
            Long itemId = Long.parseLong(payload.get("itemId").toString());
            Long memberId = Long.parseLong(payload.get("memberId").toString());

            log.info("✅ 결제 완료. paymentId = {}", paymentId);
            log.info("📦 주문 생성: itemId = {}, memberId = {}, count = {}", itemId, memberId, count);

            //주문 생성 (기존 orderService 사용)
            Long orderId = orderService.order(memberId, itemId, count);

            return ResponseEntity.ok(Map.of(
                    "status", "OK",
                    "orderId", orderId,
                    "message", "주문이 성공적으로 생성되었습니다."
            ));
        } catch (Exception e) {
            log.error("❌ 주문 생성 실패", e);
            return ResponseEntity.badRequest().body(Map.of(
                    "status", "FAIL",
                    "message", "주문 생성 중 오류 발생"
            ));
        }

/*

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

        ));*/
    }
}
